import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import {withStyles} from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import GroupIcon from '@material-ui/icons/Group';
import classNames from 'classnames';
import RemoveRedEye from '@material-ui/icons/RemoveRedEye';
import Create from '@material-ui/icons/Create';
import Delete from '@material-ui/icons/Delete';
import Typography from '@material-ui/core/Typography';
import AddIcon from '@material-ui/icons/Add';
import CloseIcon from '@material-ui/icons/Close';
import DoneIcon from '@material-ui/icons/Done';
import LockIcon from '@material-ui/icons/Lock';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import DialogActions from '@material-ui/core/DialogActions';
import Grid from '@material-ui/core/Grid';

import Layout from 'components/Layout';
import Navigator from 'components/Navigator';
import Results from 'components/Results';
import BottomTools from 'components/BottomTools';
import AddButton from 'components/AddButton';
import RowOptions from 'components/RowOptions';
import OptionButton from 'components/OptionButton';
import SearchInput from 'components/SearchInput';
import ModalDialog from 'components/ModalDialog';
import {hasAnyPermission} from 'utils/Securization';
import SecurizedComponent from 'components/SecurizedComponent';
import AddInfo from 'components/form/groups/AddInfo';
import ViewInfo from 'components/form/groups/ViewInfo';
import ViewUsers from 'components/form/groups/ViewUsers';
import ViewDBConnections from 'components/form/groups/ViewDBConnections';
import AddDBConnections from 'components/form/groups/AddDBConnection';
import AddDBConnection from 'components/form/dbconnection/AddDBConnection';
import AddUsers from 'components/form/groups/AddUsers';
import TableHC from 'components/TableHC';

import {hasPermissions} from 'utils/Securization';
import FormValidator from 'utils/FormValidator';

import {
  searchGroups,
  getGroupById,
  addGroup,
  getPaginateGroups,
  EditGroup,
  DeleteGroup
} from 'controllers/GroupController';

import {addDBConnection, getAllDBConnection} from 'controllers/DBConnectionController';
import {getMyOrganizationUnit} from 'controllers/OrganizationUnitController';

const styles = theme => ({
  palette: 'primary',
  eye: {
    color: '#388e3c'
  },
  info: {
    color: '#2196f3'
  },
  edit: {
    color: '#fdd835'
  },
  delete: {
    color: '#f44336'
  },
  lockIcon: {
    color: '#ef6c00'
  },
  optionsUser: {
    display: 'flex'
  },
  tabDBConnection: {
    paddingTop: '0.8rem'
  },
  btnAddDBConnection: {},
  tooltipDB: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  },
  PaperDB: {
    padding: '3%',
    marginTop: '1rem',
    marginBottom: '1rem'
  },
  dialogContent: {
    marginTop: '1rem',
    justifyContent: 'start-end'
  },
  title: {
    marginTop: '1.9rem',
    marginLeft: '44rem'
  },
  infoModal: {
    height: '30vh'
  },
  editModal: {
    height: '30vh'
  },
  addModal: {
    height: '30vh'
  },
  removeModal: {
    padding: '1rem'
  }
});

const head = (t) => (<TableRow>
  <TableCell></TableCell>
  <TableCell>{t('org_unit')}</TableCell>
  <TableCell>{t('name')}</TableCell>
  <TableCell>{t('description')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const registerModelDB = {
  name: '',
  host: '',
  port: '',
  database: '',
  schema: '',
  user: '',
  password: '',
  password_confirm: '',
  dbms: {}
};

const registerModel = {
  name: '',
  description: '',
  org_unit: [],
  users: [],
  dbconnections: []
};

const infoModel = {
  name: '',
  description: '',
  org_unit: [],
  users: [],
  dbconnections: []
};

const editModel = {
  name: '',
  description: '',
  org_unit: [],
  users: [],
  dbconnections: []
};

const registerValidator = new FormValidator([
  {
    field: 'name',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'org_unit',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
]);

const registerDBValidator = new FormValidator([
  {
    field: 'name',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'host',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'port',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'database',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'schema',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'user',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'password',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'

  }, {
    field: 'password_confirm',
    method: 'passwordMatch',
    validWhen: true,
    message: 'match_password'
  }, {
    field: 'password_confirm',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'dbms',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
]);

const editValidator = new FormValidator([
  {
    field: 'name',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'org_unit',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
]);

class Groups extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      allConections: [],
      page: 0,
      rowsPerPage: 5,
      count: 0,
      searching: true,
      openDialogRegister: false,
      openDialogInfo: false,
      openDialogEdit: false,
      openDialogConfirm: false,
      openPaperDBConnection: false,
      iconDB: false,
      registerModel,
      infoModel,
      editModel,
      filter: '',
      searchBy: 'name,description'
    };

    getAllDBConnection().then((allConections) => {
      this.setState({
        ...this.state,
        allConections
      })
    });

    this.updateData(false);
  }

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((GroupDTO) => {
      return (<TableRow hover={true} key={GroupDTO.id}>
        <TableCell className={classNames(classes.cellIcon)}>
          <GroupIcon/>
        </TableCell>
        <TableCell>{GroupDTO.org_unit.name}</TableCell>
        <TableCell>{GroupDTO.name}</TableCell>
        <TableCell>{GroupDTO.description}</TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={GroupDTO.id} action={this.handleClickOpenDialogInfo} tooltip={t('see_data')}>
              <RemoveRedEye className={classNames(classes.eye)}/>
            </OptionButton>
            <OptionButton id={GroupDTO.id} action={this.handleClickOpenDialogEdit} tooltip={t('edit_data')}>
              <Create className={classNames(classes.edit)}/>
            </OptionButton>
            <SecurizedComponent permissions={['ADMIN_GROUP']}>
              <OptionButton id={GroupDTO.id} action={this.handleClickOpenDialogConfirm} tooltip={t('delete_data')}>
                <Delete className={classNames(classes.delete)}/>
              </OptionButton>
            </SecurizedComponent>
          </RowOptions>
        </TableCell>
      </TableRow>);
    });

    return {data: mappedData, count: data.count};
  };

  resetState = () => {
    this.setState({
      ...this.state,
      openDialogRegister: false,
      model: registerModel
    });
  }

  componentWillMount = () => {
    if (!hasAnyPermission(['ADMIN_GROUP', 'ADMIN_GROUP_USERS'])) {
      this.props.history.replace('/');
    }
  };

  renderData = (page, rowsPerPage) => {
    const {filter} = this.state;
    const start = page * rowsPerPage;
    const size = rowsPerPage;

    if (filter === '') {
      getPaginateGroups(start, size).then(this.mapData).then((data) => this.setState({
        ...this.state,
        openDialog: false,
        count: data.count,
        page,
        rowsPerPage,
        data: data.data,
        searching: false,
        openDialogConfirm: false,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openPaperDBConnection: false,
        iconDB: false,
        registerModel,
        registerModelDB,
        infoModel,
        editModel
      }));
    } else {
      searchGroups(start, size, filter, this.state.searchBy).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page,
        rowsPerPage,
        count: data.count,
        data: data.data,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openPaperDBConnection: false,
        iconDB: false,
        registerModel,
        infoModel,
        editModel
      }));
    }

    getAllDBConnection().then((allConections) => {
      this.setState({
        ...this.state,
        allConections
      })
    });

    this.translate(registerValidator);
    this.translate(registerDBValidator);
    this.translate(editValidator);
  }

  updateData = () => {
    const {page, rowsPerPage} = this.state;
    registerValidator.hasChangedAll(false);
    editValidator.hasChangedAll(false);
    if (this.openPaperDBConnection) {
      registerDBValidator.hasChangedAll(false);
    }
    this.renderData(page, rowsPerPage);
  }

  handleChangePage = (event, page) => {
    const {rowsPerPage} = this.state;
    this.renderData(page, rowsPerPage);
  };

  handleChangeRowsPerPage = event => {
    const {page} = this.state;
    this.renderData(page, event.target.value);
  };

  handleOnDelete = () => {
    DeleteGroup(this.deleteId).then(() => {
      this.updateData(false);
    });
  };

  handleClickOpenDialogRegister = () => {
    if (hasPermissions(['ADMIN_UO'])) {
      this.setState({
        ...this.state,
        registerModel: {
          ...this.state.registerModel,
          dbconnections: [],
          users: []
        },
        openDialogRegister: true
      });
    } else {
      getMyOrganizationUnit().then((UO) => {
        this.setState({
          ...this.state,
          registerModel: {
            ...this.state.registerModel,
            org_unit: UO,
            dbconnections: [],
            users: []
          },
          openDialogRegister: true
        });
      });
    }
  }

  handleClickOpenDialogConfirm = (id) => {
    this.deleteId = id;
    this.setState({openDialogConfirm: true});
  };

  handleClickOpenDialogEdit = (id, evt) => {
    getGroupById(id).then((model) => {
      this.setState({
        ...this.state,
        editModel: {
          ...model,
          id
        },
        openDialogEdit: true
      });
    });
  };

  handlerOnClickDBForm = () => {
    this.setState({
      ...this.state,
      openPaperDBConnection: !this.state.openPaperDBConnection,
      iconDB: !this.state.iconDB
    })
  }

  handlerOnChangePermissions = () => {};

  handlerDeleteSelectedUsers = (name) => (id) => {
    const {registerModel, editModel} = this.state;

    let users = [];
    if (name === "registerModel") {
      users = registerModel.users.filter(u => u.value !== id);
    } else {
      users = editModel.users.filter(u => {
        const utilID = (!u.id)
          ? u.value
          : u.id;
        return utilID !== id
      });
    }

    this.setState({
      ...this.state,
      [name]: {
        ...this.state[name],
        users
      }
    })
  };

  handleClickOpenDialogInfo = (id, evt) => {
    getGroupById(id).then((model) => {
      this.setState({
        ...this.state,
        infoModel: model,
        openDialogInfo: true
      });
    });
  };

  handleCloseDialogConfirm = () => {
    this.updateData(false);
  };

  handleCloseDialog = () => {
    this.updateData(false);
  };

  handlerUpdateDataDB = () => {
    getAllDBConnection().then((allConections) => {
      this.setState({
        ...this.state,
        allConections
      })
    });
  }

  handleOnSave = (e) => {
    const {registerModel} = this.state;
    registerValidator.hasChangedAll(true);
    const validate = registerValidator.validate(registerModel);
    if (validate.isValid === true) {
      addGroup(registerModel).then(() => {
        this.handleCloseDialog();
      }).catch((err) => alert(err));
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleOnSaveDB = (e) => {
    const {registerModelDB} = this.state;
    registerDBValidator.hasChangedAll(true);
    const validate = registerDBValidator.validate(registerModelDB);
    if (validate.isValid === true) {
      addDBConnection(registerModelDB).then(() => {
        this.setState({
          ...this.state,
          openPaperDBConnection: false,
          iconDB: false
        });
        this.handlerUpdateDataDB();
      }).catch((err) => alert(err));
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleOnEdit = (e) => {
    const {editModel} = this.state;
    editValidator.hasChangedAll(true);
    const validate = editValidator.validate(editModel);
    if (validate.isValid === true) {
      EditGroup(editModel.id, editModel).then(() => {
        this.updateData(false);
      });
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handlerOnChangeRegister = (e) => {
    registerValidator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      registerModel: {
        ...this.state.registerModel,
        [e.target.name]: e.target.value
      }
    });
  };

  handlerOnChangeRegisterDB = (e) => {
    registerDBValidator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      registerModelDB: {
        ...this.state.registerModelDB,
        [e.target.name]: e.target.value
      }
    });
  };

  handlerOnChangeEdit = (e) => {
    editValidator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      editModel: {
        ...this.state.editModel,
        [e.target.name]: e.target.value
      }
    });
  };

  onFilter = (e) => {
    const value = e.target.value;
    if (value.trim() !== '') {
      this.setState({
        ...this.state,
        searching: true
      })
      searchGroups(0, this.state.rowsPerPage, value, this.state.searchBy).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page: 0,
        count: data.count,
        data: data.data,
        searching: false,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        registerModel,
        infoModel,
        editModel,
        filter: value
      }));
    } else {
      this.setState({
        ...this.state,
        page: 0,
        filter: value
      }, () => {
        this.updateData(false);
      })
    }
  };

  translate(validations) {
    const {t} = this.props;
    validations.validations.map(m => m.message = t(m.message));
  }

  render() {
    const {classes, t} = this.props;
    const {
      registerModel,
      registerModelDB,
      infoModel,
      editModel,
      openDialogInfo,
      openDialogEdit,
      openDialogRegister,
      openDialogConfirm,
      openPaperDBConnection,
      iconDB,
      allConections
    } = this.state;

    let utilModel = [];
    let utilNameModel = '';
    if (registerModel.users.length === 0) {
      utilModel = editModel.users;
      utilNameModel = 'editModel';
    } else {
      utilModel = registerModel.users;
      utilNameModel = 'registerModel';
    }

    const rows = utilModel.map(user => {
      const utilName = (!user.label)
        ? user.name
        : user.label;
      const utilID = (!user.value)
        ? user.id
        : user.value;
      const usersOptions = (<div className={classes.optionsUser}>
        <OptionButton id={utilID} name={user.label} action={this.handlerDeleteSelectedUsers(utilNameModel)} tooltip={t('delete_data')}>
          <Delete className={classNames(classes.delete)}/>
        </OptionButton>
        <OptionButton id={utilID} name={user.label} action={this.handlerOnChangePermissions} tooltip={t('permissions')}>
          <LockIcon className={classNames(classes.lockIcon)}/>
        </OptionButton>
      </div>);
      return [utilName, usersOptions]
    });

    const validateRegister = registerValidator.validate(registerModel);
    const validateRegisterDB = openPaperDBConnection
      ? registerDBValidator.validate(registerModelDB)
      : '';
    const validateEdit = editValidator.validate(editModel);

    return (<Layout history={this.props.history}>
      <div className={classes.container}>
        <Navigator>
          <SearchInput onInput={this.onFilter}/>
          <Typography color={'primary'} variant={'title'} className={classNames(classes.title)}>{t('groups')}</Typography>
        </Navigator>
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t)} data={this.state.data} searching={this.state.searching}/>
        <BottomTools>
          <AddButton action={this.handleClickOpenDialogRegister} tooltip={t('add_group')} permissions={['ADMIN_GROUP']}/>
        </BottomTools>

        {/* Delete modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.removeModal
          }} openModal={openDialogConfirm} openDialog={this.handleClickOpenDialogConfirm} closeDialog={this.handleCloseDialogConfirm} textButton={t('confirm')} saveAction={this.handleOnDelete}>
          <Typography align='center' variant='title'>
            {t('ask_confirm')}
          </Typography>
        </ModalDialog>

        {/* Add modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.addModal
          }} openModal={openDialogRegister} openDialog={this.handleClickOpenDialogRegister} closeDialog={this.handleCloseDialog} title={t('group_data')} saveAction={this.handleOnSave} textButton={t('save')} childrenComponents={[
            {
              label: t('info_users'),
              component: (<AddInfo model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}/>)
            }, {
              label: t('dbconnections_add'),
              component: (<Grid container={true} spacing={16} className={classNames(classes.tabDBConnection)}>
                <Grid item={true} sm={11}>
                  <AddDBConnections allConections={allConections} model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}/>
                </Grid>
                <Grid item={true} sm={1}>
                  <Button className={classNames(classes.btnAddDBConnection)} onClick={this.handlerOnClickDBForm} variant="fab" mini={true} color="primary" aria-label="Add">
                    {
                      iconDB
                        ? <CloseIcon/>
                        : <AddIcon/>
                    }
                  </Button>
                </Grid>
                <Grid item={true} sm={12}>
                  {
                    openPaperDBConnection
                      ? <Paper className={classNames(classes.PaperDB)}><AddDBConnection model={registerModelDB} onChange={this.handlerOnChangeRegisterDB} validator={validateRegisterDB}/>
                          <DialogActions className={classNames(classes.dialogContent)}>
                            <Button variant="fab" mini={true} className={classNames(classes.btn)} onClick={this.handleOnSaveDB} color="primary"><DoneIcon/></Button>
                          </DialogActions>
                        </Paper>
                      : ''
                  }
                </Grid>
              </Grid>)
            }, {
              label: t('add_users'),
              component: (<React.Fragment>
                <AddUsers model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}/> {
                  utilModel.length !== 0
                    ? <TableHC header={[t('username'), t('options')]} rows={rows}/>
                    : <div>
                        <Typography align='center' color={'primary'} variant='body1'>{t('not_selected_users')}</Typography>
                      </div>
                }
              </React.Fragment>)
            }
          ]}/> {/* Info modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.infoModal
          }} openModal={openDialogInfo} openDialog={this.handleClickOpenDialogInfo} closeDialog={this.handleCloseDialog} disableActionButtons={true} title={t('info_groups')} childrenComponents={[
            {
              label: t('info_users'),
              component: (<ViewInfo model={infoModel}/>)
            }, {
              label: t('dbconnections_add'),
              component: (<ViewDBConnections model={infoModel}/>)
            }, {
              label: t('add_users'),
              component: (<ViewUsers model={infoModel}/>)
            }
          ]}/> {/* Edit modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.editModal
          }} openModal={openDialogEdit} openDialog={this.handleClickOpenDialogRegister} closeDialog={this.handleCloseDialog} title={t('group_data')} saveAction={this.handleOnEdit} textButton={t('save')} childrenComponents={[
            {
              label: t('info_users'),
              component: (<AddInfo model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}/>)
            }, {
              label: t('dbconnections_add'),
              component: (<Grid container={true} spacing={16} className={classNames(classes.tabDBConnection)}>
                <Grid item={true} sm={11}>
                  <AddDBConnections allConections={allConections} model={editModel} onChange={this.handlerOnChangeEdit} validator={validateRegister}/>
                </Grid>
                <Grid item={true} sm={1}>
                  <Button className={classNames(classes.btnAddDBConnection)} onClick={this.handlerOnClickDBForm} variant="fab" mini={true} color="primary" aria-label="Add">
                    {
                      iconDB
                        ? <CloseIcon/>
                        : <AddIcon/>
                    }
                  </Button>
                </Grid>
                <Grid item={true} sm={12}>
                  {
                    openPaperDBConnection
                      ? <Paper className={classNames(classes.PaperDB)}><AddDBConnection model={registerModelDB} onChange={this.handlerOnChangeRegisterDB} validator={validateRegisterDB}/>
                          <DialogActions className={classNames(classes.dialogContent)}>
                            <Button variant="fab" mini={true} className={classNames(classes.btn)} onClick={this.handleOnSaveDB} color="primary"><DoneIcon/></Button>
                          </DialogActions>
                        </Paper>
                      : ''
                  }
                </Grid>
              </Grid>)
            }, {
              label: t('add_users'),
              component: (<React.Fragment>
                <AddUsers model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}/> {
                  utilModel.length !== 0
                    ? <TableHC header={[t('username'), t('options')]} rows={rows}/>
                    : <div>
                        <Typography align='center' color={'primary'} variant='body1'>{t('not_selected_users')}</Typography>
                      </div>
                }
              </React.Fragment>)
            }
          ]}/>
      </div>
    </Layout>);
  }
}
Groups.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(Groups));
