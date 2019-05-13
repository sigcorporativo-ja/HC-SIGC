import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import {withStyles} from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import classNames from 'classnames';
import RemoveRedEye from '@material-ui/icons/RemoveRedEye';
import Create from '@material-ui/icons/Create';
import Delete from '@material-ui/icons/Delete';
import InboxIcon from '@material-ui/icons/Inbox';
import Typography from '@material-ui/core/Typography';

import Layout from 'components/Layout';
import Navigator from 'components/Navigator';
import Results from 'components/Results';
import BottomTools from 'components/BottomTools';
import AddButton from 'components/AddButton';
import RowOptions from 'components/RowOptions';
import OptionButton from 'components/OptionButton';
import SearchInput from 'components/SearchInput';
import ModalDialog from 'components/ModalDialog';
import SecurizedComponent from 'components/SecurizedComponent';
import {hasPermissions} from 'utils/Securization';
import FormValidator from 'utils/FormValidator';
import AddDBMSs from 'components/form/dbms/AddDBMSs';
import EditDBMSs from 'components/form/dbms/EditDBMSs';
import ViewDBMSs from 'components/form/dbms/ViewDBMSs';

import {
  searchDBMSs,
  addDBMS,
  EditDBMS,
  DeleteDBMS,
  getDBMSById,
  getPaginateDBMS
} from 'controllers/DBMSController';

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
  title: {
    marginTop: '1.9rem',
    marginLeft: '41rem'
  },
  infoModal: {
    height: '20vh',
    width: '40vh'
  },
  editModal: {
    height: '20vh',
    width: '40vh'
  },
  addModal: {
    height: '20vh',
    width: '40vh'
  },
  removeModal: {
    padding: '1rem'
  }
});

const head = (t) => (<TableRow>
  <TableCell></TableCell>
  <TableCell>{t('name')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const registerModel = {
  name: '',
  connectionRegex: ''
};

const infoModel = {
  name: '',
  connectionRegex: ''
};

const editModel = {
  name: '',
  connectionRegex: ''
};

const registerValidator = new FormValidator([
  {
    field: 'name',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'connectionRegex',
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
    field: 'connectionRegex',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
]);

class DBMS extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      page: 0,
      rowsPerPage: 5,
      count: 0,
      searching: true,
      openDialogConfirm: false,
      openDialogRegister: false,
      openDialogEdit: false,
      openDialogInfo: false,
      openDialogNoDelete: false,
      registerModel,
      infoModel,
      editModel,
      filter: '',
      searchBy: 'name'
    };
    this.updateData();
  }

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((DBMSDTO) => {
      return (<TableRow hover={true} key={DBMSDTO.id}>
        <TableCell className={classNames(classes.cellIcon)}>
          <InboxIcon/>
        </TableCell>
        <TableCell>{DBMSDTO.name}</TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={DBMSDTO.id} action={this.handleClickOpenDialogInfo} tooltip={t('see_data')}>
              <RemoveRedEye className={classNames(classes.eye)}/>
            </OptionButton>
            <SecurizedComponent permissions={['ADMIN_UO']}>
              <RowOptions>
                <OptionButton id={DBMSDTO.id} action={this.handleClickOpenDialogEdit} tooltip={t('edit_data')}>
                  <Create className={classNames(classes.edit)}/>
                </OptionButton>
                <OptionButton id={DBMSDTO.id} action={this.handleClickOpenDialogConfirm} tooltip={t('delete_data')}>
                  <Delete className={classNames(classes.delete)}/>
                </OptionButton>
              </RowOptions>
            </SecurizedComponent>
          </RowOptions>
        </TableCell>
      </TableRow>);
    });

    return {data: mappedData, count: data.count};
  };

  componentWillMount = () => {
    if (!hasPermissions(['ADMIN_DATABASES'])) {
      this.props.history.replace('/');
    }
  };

  renderData = (page, rowsPerPage) => {
    const {filter} = this.state;
    const start = (page) * rowsPerPage;
    const size = rowsPerPage;
    if (filter === '') {
      getPaginateDBMS(start, size).then(this.mapData).then((data) => this.setState({
        ...this.state,
        count: data.count,
        page,
        rowsPerPage,
        data: data.data,
        searching: false,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openDialogNoDelete: false,
        registerModel,
        infoModel,
        editModel
      }));
    } else {
      searchDBMSs(start, size, filter, this.state.searchBy).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page,
        rowsPerPage,
        count: data.count,
        data: data.data,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openDialogNoDelete: false,
        registerModel,
        infoModel,
        editModel
      }));
    }

    this.translate(registerValidator);
    this.translate(editValidator);
  }

  // Handle methods -----------------------------------------------------------

  handleChangePage = (event, page) => {
    this.renderData(page, this.state.rowsPerPage);
  };

  handleDelete = (id, evt) => {
    DeleteDBMS(id).then(() => {
      this.updateData();
    });
  };

  handleChangeRowsPerPage = event => {
    this.renderData(this.state.page, event.target.value);
  };

  handleOnDelete = () => {
    DeleteDBMS(this.deleteId).then(() => {
      this.updateData();
    }).catch(err => {
      this.handleClickOpenDialogNoDelete();
    });
  };

  handleOnSave = (e) => {
    const {registerModel} = this.state;
    registerValidator.hasChangedAll(true);
    const validate = registerValidator.validate(registerModel);
    if (validate.isValid === true) {
      addDBMS(registerModel).then(() => {
        this.handleCloseDialogRegister();
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
      EditDBMS(editModel.id, editModel).then(() => {
        this.updateData();
      });
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleClickOpenDialogRegister = () => {
    this.setState({openDialogRegister: true});
  };

  handleClickOpenDialogInfo = (id, evt) => {
    getDBMSById(id).then((model) => {
      this.setState({
        ...this.state,
        infoModel: model,
        openDialogInfo: true
      });
    });
  };

  handleClickOpenDialogEdit = (id, evt) => {
    getDBMSById(id).then((model) => {
      this.setState({
        ...this.state,
        editModel: model,
        openDialogEdit: true
      });
    });
  };

  handleClickOpenDialogConfirm = (id) => {
    this.deleteId = id;
    this.setState({openDialogConfirm: true});
  };

  handleClickOpenDialogNoDelete = () => {
    this.setState({openDialogNoDelete: true});
  }

  handleCloseDialogRegister = () => {
    this.updateData();
  };

  handleCloseDialogInfo = () => {
    this.setState({openDialogInfo: false});
  };

  handleCloseDialogEdit = () => {
    this.updateData();
  };

  handleCloseDialogConfirm = () => {
    this.updateData();
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

  // More methods -------------------------------------------------------------

  updateData = () => {
    registerValidator.hasChangedAll(false);
    editValidator.hasChangedAll(false);
    this.renderData(this.state.page, this.state.rowsPerPage);
  }

  formRegisterRefCallback = (instance) => {
    this.formRegister = instance;
  }

  formEditRefCallback = (instance) => {
    this.formEdit = instance;
  }

  onFilter = (e) => {
    const value = e.target.value;
    if (value.trim() !== '') {
      this.setState({
        ...this.state,
        searching: true
      })
      searchDBMSs(0, this.state.rowsPerPage, value, this.state.searchBy).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page: 0,
        count: data.count,
        data: data.data,
        searching: false,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openDialogNoDelete: false,
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
        this.updateData();
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
      infoModel,
      editModel,
      openDialogInfo,
      openDialogEdit,
      openDialogRegister,
      openDialogConfirm,
      openDialogNoDelete
    } = this.state;

    const validateRegister = registerValidator.validate(registerModel);
    const validateEdit = editValidator.validate(editModel);

    return (<Layout history={this.props.history}>
      <div className={classes.container}>
        <Navigator>
          <SearchInput onInput={this.onFilter}/>
          <Typography variant={'title'} color={'primary'} className={classNames(classes.title)}>{t('dbms')}</Typography>
        </Navigator>
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t)} data={this.state.data} searching={this.state.searching}/>
        <SecurizedComponent permissions={['ADMIN_UO']}>
          <BottomTools>
            <AddButton action={this.handleClickOpenDialogRegister} tooltip={t('dbms_add')}/>
          </BottomTools>
        </SecurizedComponent>

        {/* DBMS can not be deleted */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.removeModal
          }} openModal={openDialogNoDelete} closeDialog={this.updateData} disableActionButtons={true}>
          <Typography align='center' variant='title'>
            {t('no_delete_dbms')}
          </Typography>
        </ModalDialog>

        {/* Delete modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.removeModal
          }} openModal={openDialogConfirm} closeDialog={this.handleCloseDialogConfirm} textButton={t('confirm')} saveAction={this.handleOnDelete}>
          <Typography align='center' variant='title'>
            {t('ask_confirm')}
          </Typography>
        </ModalDialog>

        {/* Add modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.addModal
          }} openModal={openDialogRegister} closeDialog={this.handleCloseDialogRegister} title={t('dbms_data')} saveAction={this.handleOnSave} textButton={t('save')}>
          <AddDBMSs AddDBMSs="AddDBMSs" model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}></AddDBMSs>
        </ModalDialog>

        {/* Edit modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.editModal
          }} openModal={openDialogEdit} closeDialog={this.handleCloseDialogEdit} title={t('dbms_edit')} saveAction={this.handleOnEdit} textButton={t('save')}>
          <EditDBMSs EditDBMSs="EditDBMSs" model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}></EditDBMSs>
        </ModalDialog>

        {/* Info modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.infoModal
          }} openModal={openDialogInfo} closeDialog={this.handleCloseDialogInfo} disableActionButtons={true} title={t('dbms_info')}>
          <ViewDBMSs ViewDBMSs="ViewDBMSs" model={infoModel}></ViewDBMSs>
        </ModalDialog>
      </div>
    </Layout>);
  }
}
DBMS.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(DBMS));
