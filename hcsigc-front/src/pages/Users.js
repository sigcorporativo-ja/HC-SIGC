import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import {withStyles} from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import classNames from 'classnames';
import RemoveRedEye from '@material-ui/icons/RemoveRedEye';
import Create from '@material-ui/icons/Create';
import Person from '@material-ui/icons/Person';
import Delete from '@material-ui/icons/Delete';

import Layout from 'components/Layout';
import Navigator from 'components/Navigator';
import Results from 'components/Results';
import BottomTools from 'components/BottomTools';
import AddButton from 'components/AddButton';
import RowOptions from 'components/RowOptions';
import OptionButton from 'components/OptionButton';
import SearchInput from 'components/SearchInput';
import ModalDialog from 'components/ModalDialog';
import {hasPermissions} from 'utils/Securization';
import AddInfo from 'components/form/users/AddInfo';
import ViewInfo from 'components/form/users/ViewInfo';
import EditInfo from 'components/form/users/EditInfo';
import AddPermissions from 'components/form/users/AddPermissions';
import FormValidator from 'utils/FormValidator';

import {
  searchUsers,
  addUser,
  getPaginateUsers,
  EditUsers,
  DeleteUsers,
  getUserById,
  getPermissionsGrant,
  getRolesGrant,
  getRoleById
} from 'controllers/UserController';
import {getUserSession} from 'controllers/AuthController';
import {getAllOrganizationUnits, getMyOrganizationUnit} from 'controllers/OrganizationUnitController';
import {getAllConfiguration} from 'controllers/ConfigurationController';

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
  cellIcon: {
    // paddingRight: 0
  },
  permissionsCheckbox: {
    backgroundColor: theme.palette.primary.main
  },
  textPass: {
    marginTop: '3rem',
    paddingBottom: '-20px'
  },
  title: {
    marginTop: '1.9rem',
    marginLeft: '44rem'
  },
  infoModal: {
    height: '60vh',
    width: '40vh'
  },
  editModal: {
    height: '55vh',
    width: '40vh'
  },
  addModal: {
    height: '65vh',
    width: '40vh'
  },
  removeModal: {
    padding: '1rem'
  }
});

const head = (t) => (<TableRow>
  <TableCell></TableCell>
  <TableCell>{t('org_unit')}</TableCell>
  <TableCell>{t('username')}</TableCell>
  <TableCell>{t('mail')}</TableCell>
  <TableCell>{t('name')}</TableCell>
  <TableCell>{t('surname')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const registerModel = {
  name: '',
  surname: '',
  username: '',
  email: '',
  quota: '',
  password: '',
  password_confirm: '',
  ldap: 'false',
  org_unit: ''
};

const infoModel = {
  name: '',
  surname: '',
  username: '',
  email: '',
  quota: '',
  usedQuota: '',
  org_unit: '',
  permissions: []
};

const editModel = {
  name: '',
  surname: '',
  username: '',
  password: '',
  password_confirm: '',
  value: '',
  conf: '',
  email: '',
  quota: '',
  org_unit: '',
  permissions: []
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
  }, {
    field: 'surname',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'username',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'quota',
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
    field: 'email',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'

  }, {
    field: 'email',
    method: 'isEmail',
    validWhen: true,
    message: 'format_email'
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
  }, {
    field: 'surname',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'username',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'quota',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'password_confirm',
    method: 'passwordMatch',
    validWhen: true,
    message: 'match_password'
  }, {
    field: 'password',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'email',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'

  }, {
    field: 'email',
    method: 'isEmail',
    validWhen: true,
    message: 'format_email'
  }
]);

class Users extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      page: 0,
      rowsPerPage: 5,
      count: 0,
      quota: 0,
      optionSelect: '',
      optionsSelect: [],
      searching: true,
      openDialogRegister: false,
      openDialogInfo: false,
      openDialogEdit: false,
      openDialogConfirm: false,
      registerModel,
      infoModel,
      editModel,
      permissions: [],
      checkedPermissions: [],
      roles: [],
      head,
      utilPassword: '',
      filter: '',
      searchBy: 'username,mail,name,surname',
      suggestions: [],
      selectedOU: '',
      selectedRole: -1
    };
    this.updateData();
    this.userSession = getUserSession();
  }

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((UserDTO) => {
      return (<TableRow hover={true} key={UserDTO.id}>
        <TableCell className={classNames(classes.cellIcon)}>
          <Person/>
        </TableCell>
        <TableCell>{UserDTO.org_unit.name}</TableCell>
        <TableCell>{UserDTO.username}</TableCell>
        <TableCell>{UserDTO.email}</TableCell>
        <TableCell>{UserDTO.name}</TableCell>
        <TableCell>{UserDTO.surname}</TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={UserDTO.id} action={this.handleClickOpenInfoDialog} tooltip={t('see_data')}>
              <RemoveRedEye className={classNames(classes.eye)}/>
            </OptionButton>
            <OptionButton id={UserDTO.id} action={this.handleClickOpenEditDialog} tooltip={t('edit_data')}>
              <Create className={classNames(classes.edit)}/>
            </OptionButton>
            <OptionButton id={UserDTO.id} action={this.handleClickOpenDialogConfirm} tooltip={t('delete_data')}>
              <Delete className={classNames(classes.delete)}/>
            </OptionButton>
          </RowOptions>
        </TableCell>
      </TableRow>);
    });

    return {data: mappedData, count: data.count};
  };

  componentWillMount = () => {
    if (!hasPermissions(['ADMIN_USERS'])) {
      this.props.history.replace('/');
    }
  };

  renderData = (page, rowsPerPage) => {
    const {filter} = this.state;
    const start = (page) * rowsPerPage;
    const size = rowsPerPage;
    if (filter === '') {
      getPaginateUsers(start, size).then(this.mapData).then((data) => this.setState({
        ...this.state,
        count: data.count,
        page,
        head,
        rowsPerPage,
        data: data.data,
        searching: false,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        registerModel,
        infoModel,
        editModel
      }));
    } else {
      searchUsers(start, size, filter, this.state.searchBy).then(this.mapData).then((data) => {
        this.setState({
          ...this.state,
          count: data.count,
          page,
          rowsPerPage,
          data: data.data,
          openDialogRegister: false,
          openDialogInfo: false,
          openDialogEdit: false,
          openDialogConfirm: false,
          registerModel,
          infoModel,
          editModel
        });
      });
    }

    if (hasPermissions(['ADMIN_UO'])) {
      getAllOrganizationUnits().then((allOu) => {
        const forAllOu = allOu.map((obj) => {
          return {value: obj.id, label: obj.name}
        });
        this.setState({
          ...this.state,
          optionsSelect: forAllOu,
          disabledSelect: false
        })
      });
    } else {
      getMyOrganizationUnit().then((organizationUnit) => {
        const listOU = [organizationUnit];
        const forAllOu = listOU.map((obj) => {
          return {value: obj.id, label: obj.name}
        });
        this.setState({
          ...this.state,
          optionsSelect: forAllOu,
          disabledSelect: true
        })
      });
    }

    this.translate(registerValidator);
    this.translate(editValidator);

  }

  updateData = () => {
    registerValidator.hasChangedAll(false);
    editValidator.hasChangedAll(false);
    this.renderData(this.state.page, this.state.rowsPerPage);
  }

  handleChangePage = (event, page) => {
    this.renderData(page, this.state.rowsPerPage);
  };

  handleOnDelete = () => {
    DeleteUsers(this.deleteId).then(() => {
      this.updateData();
    });
  };

  handleClickOpenDialogConfirm = (id) => {
    this.deleteId = id;
    this.setState({openDialogConfirm: true});
  };

  handleChangeRowsPerPage = event => {
    this.renderData(this.state.page, event.target.value);
  };

  handleOnSave = (e) => {
    const {registerModel} = this.state;
    registerValidator.hasChangedAll(true);
    const validate = registerValidator.validate(registerModel);
    if (validate.isValid === true) {
      addUser(registerModel).then(() => {
        this.handleCloseDialog();
      }).catch((error) => alert(error));
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
      EditUsers(editModel.id, editModel).then(() => {
        this.updateData();
      });
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleClickOpenDialogRegister = () => {
    getAllConfiguration().then((config) => {
      if (hasPermissions(['ADMIN_UO'])) {
        this.setState({
          ...this.state,
          registerModel: {
            ...registerModel,
            quota: config[0].value
          },
          openDialogRegister: true
        })
      } else {
        getMyOrganizationUnit().then((UO) => {
          this.setState({
            ...this.state,
            registerModel: {
              ...this.state.registerModel,
              org_unit: UO,
              quota: config[0].value
            },
            openDialogRegister: true
          });
        });
      }
    });
  };

  handleClickOpenInfoDialog = (id, evt) => {
    getUserById(id).then((model) => {
      getPermissionsGrant(this.userSession.id).then(data => {
        getRolesGrant(data).then(roles => {
          this.setState({
            ...this.state,
            infoModel: model,
            openDialogInfo: true,
            permissions: data,
            roles
          });
        })
      });
    });
  };

  handleClickOpenEditDialog = (id, evt) => {
    getUserById(id).then((model) => {
      getPermissionsGrant(this.userSession.id).then(data => {
        getRolesGrant(data).then(roles => {
          this.setState({
            ...this.state,
            editModel: model,
            openDialogEdit: true,
            permissions: data,
            roles
          });
        });
      });
    });
  };

  handleCloseDialog = () => {
    this.updateData();
  };

  handlePermissions = data => e => {
    if (data !== null) {
      this.setState({
        ...this.state,
        editModel: {
          ...this.state.editModel,
          permissions: e.target.checked === true
            ? this.state.editModel.permissions.concat([data])
            : this.state.editModel.permissions.filter(permission => permission.id !== data.id)
        }
      });
    } else {
      const roleId = e.target.value;
      getRoleById(roleId).then(role => {
        this.setState({
          ...this.state,
          selectedRole: roleId,
          editModel: {
            ...this.state.editModel,
            permissions: role.permissions
          }
        })
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
      searchUsers(0, this.state.rowsPerPage, value, this.state.searchBy).then(this.mapData).then((data) => {
        this.setState({
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
        });
      });
    } else {
      this.setState({
        ...this.state,
        page: 0,
        filter: value
      }, () => {
        this.updateData();
      })
    }
  }

  translate(validations) {
    const {t} = this.props;
    validations.validations.map(m => m.message = t(m.message));
  }

  render() {
    const {classes, t} = this.props;
    let {registerModel, editModel} = this.state;
    const {
      infoModel,
      openDialogInfo,
      openDialogEdit,
      openDialogRegister,
      openDialogConfirm,
      permissions,
      roles,
      head,
      selectedRole
    } = this.state;

    if (registerModel.password_confirm === undefined) {
      registerModel = {
        ...registerModel,
        password_confirm: ''
      }
    }
    const validateRegister = registerValidator.validate(registerModel);
    if (editModel.password === undefined) {
      editModel = {
        ...editModel,
        password: ''
      }
    }
    if (editModel.password_confirm === undefined) {
      editModel = {
        ...editModel,
        password_confirm: ''
      }
    }
    const validateEdit = editValidator.validate(editModel);

    return (<Layout history={this.props.history}>
      <div className={classes.container}>
        <Navigator onFilter={this.onFilter}>
          <SearchInput onInput={this.onFilter}/>
          <Typography color={'primary'} variant={'title'} className={classNames(classes.title)}>{t('users')}</Typography>
        </Navigator>
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t)} data={this.state.data} searching={this.state.searching}/>
        <BottomTools>
          <AddButton action={this.handleClickOpenDialogRegister} tooltip={t('add_user')}/>
        </BottomTools>

        {/* Add modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.addModal
          }} openModal={openDialogRegister} openDialog={this.handleClickOpenDialogRegister} closeDialog={this.handleCloseDialog} title={t('userData')} saveAction={this.handleOnSave} textButton={t('save')} childrenComponents={[{
              label: t('add_data'),
              component: (<AddInfo AddInfo="AddInfo" model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}/>)
            }
          ]}/> {/* Edit modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.editModal
          }} openModal={openDialogEdit} openDialogEdit={this.handleClickOpenEditDialog} closeDialog={this.handleCloseDialog} title={t('edit_user')} saveAction={this.handleOnEdit} textButton={t('save')} childrenComponents={[
            {
              label: t('edit_data'),
              component: (<EditInfo AddInfo="AddInfo" model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}/>)
            }, {
              label: t('permissions_edit'),
              component: (<AddPermissions onChange={this.handlePermissions} model={editModel} selectedRole={selectedRole} permissions={permissions} roles={roles}/>)
            }
          ]}></ModalDialog>

        {/* Info modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.infoModal
          }} openModal={openDialogInfo} openDialog={this.handleClickOpenInfoDialog} closeDialog={this.handleCloseDialog} disableActionButtons={true} title={t('info_users')} childrenComponents={[
            {
              label: t('info'),
              component: (<ViewInfo model={infoModel}/>)
            }, {
              label: t('permissions'),
              component: (<AddPermissions disabled={true} model={infoModel} selectedRole={selectedRole} permissions={permissions} roles={roles}/>)
            }
          ]}></ModalDialog>

        {/* Delete modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.removeModal
          }} openModal={openDialogConfirm} closeDialog={this.handleCloseDialog} textButton={t('confirm')} saveAction={this.handleOnDelete}>
          <Typography align='center' variant='title'>
            {t('ask_confirm')}
          </Typography>
        </ModalDialog>

      </div>
    </Layout>);
  }
}

Users.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(Users));
