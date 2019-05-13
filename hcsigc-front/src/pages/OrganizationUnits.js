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
import AccountBalance from '@material-ui/icons/AccountBalance';
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
import {hasPermissions} from 'utils/Securization';
import FormValidator from 'utils/FormValidator';
import AddOrganizationUnits from 'components/form/organizationUnits/AddOrganizationUnit';
import EditOrganizationUnits from 'components/form/organizationUnits/EditOrganizationUnit';
import ViewOrganizationUnits from 'components/form/organizationUnits/ViewOrganizationUnit';

import {
  searchOrganizationUnits,
  getOrganizationUnitById,
  AddOrganizationUnit,
  getPaginateOrganizationUnits,
  EditOrganizationUnit,
  DeleteOrganizationUnit
} from 'controllers/OrganizationUnitController';

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
  disableInput: {
    color: '#000'
  },
  title: {
    marginTop: '1.9rem',
    marginLeft: '36rem'
  },
  infoModal: {
    height: '20vh',
    width: '20vw'
  },
  editModal: {
    height: '20vh',
    width: '20vw'
  },
  addModal: {
    height: '20vh'
  },
  removeModal: {
    padding: '1rem'
  }
});

const head = (t) => (<TableRow>
  <TableCell></TableCell>
  <TableCell>{t('name')}</TableCell>
  <TableCell>{t('description')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const registerModel = {
  name: '',
  description: ''
};

const infoModel = {
  name: '',
  description: ''
};

const editModel = {
  name: '',
  description: ''
};

const registerValidator = new FormValidator([
  {
    field: 'name',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'description',
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
    field: 'description',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
]);

class OrganizationUnits extends Component {
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
      openDialogInfo: false,
      openDialogEdit: false,
      openDialogNoDelete: false,
      registerModel,
      infoModel,
      editModel,
      head,
      filter: '',
      searchBy: 'name,description'
    };
    this.updateData();
  }

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((OrganizationUnitDTO) => {
      return (<TableRow hover={true} key={OrganizationUnitDTO.id}>
        <TableCell className={classNames(classes.cellIcon)}>
          <AccountBalance/>
        </TableCell>
        <TableCell>{OrganizationUnitDTO.name}</TableCell>
        <TableCell>{OrganizationUnitDTO.description}</TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={OrganizationUnitDTO.id} action={this.handleClickOpenDialogInfo} tooltip={t('see_data')}>
              <RemoveRedEye className={classNames(classes.eye)}/>
            </OptionButton>
            <OptionButton id={OrganizationUnitDTO.id} action={this.handleClickOpenDialogEdit} tooltip={t('edit_data')}>
              <Create className={classNames(classes.edit)}/>
            </OptionButton>
            <OptionButton id={OrganizationUnitDTO.id} action={this.handleClickOpenDialogConfirm} tooltip={t('delete_data')}>
              <Delete className={classNames(classes.delete)}/>
            </OptionButton>
          </RowOptions>
        </TableCell>
      </TableRow>);
    });

    return {data: mappedData, count: data.count};
  };

  componentWillMount = () => {
    if (!hasPermissions(['ADMIN_UO'])) {
      this.props.history.replace('/');
    }
  };

  renderData = (page, rowsPerPage) => {
    const {filter} = this.state;
    const start = (page) * rowsPerPage;
    const size = rowsPerPage;
    if (filter === '') {
      getPaginateOrganizationUnits(start, size).then(this.mapData).then((data) => this.setState({
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
        editModel,
        head
      }));
    } else {
      searchOrganizationUnits(start, size, filter, this.state.searchBy).then(this.mapData).then((data) => this.setState({
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

  handleChangePage = (event, page) => {
    this.renderData(page, this.state.rowsPerPage);
  };

  handleOnDelete = () => {
    DeleteOrganizationUnit(this.deleteId).then(() => {
      this.updateData();
    }).catch(err => {
      this.handleClickOpenDialogNoDelete();
    });
  };

  updateData = () => {
    registerValidator.hasChangedAll(false);
    editValidator.hasChangedAll(false);
    this.renderData(this.state.page, this.state.rowsPerPage);
  }

  handleChangeRowsPerPage = event => {
    this.renderData(this.state.page, event.target.value);
  };

  handleClickOpenDialogRegister = () => {
    this.setState({openDialogRegister: true});
  };

  handleClickOpenDialogEdit = (id, evt) => {
    getOrganizationUnitById(id).then((model) => {
      this.setState({
        ...this.state,
        editModel: model,
        openDialogEdit: true
      });
    });
  };

  handleClickOpenDialogInfo = (id, evt) => {
    getOrganizationUnitById(id).then((model) => {
      this.setState({
        ...this.state,
        infoModel: model,
        openDialogInfo: true
      });
    });
  };

  handleCloseDialog = () => {
    this.updateData();
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
    this.updateData();
  };

  handleCloseDialogEdit = () => {
    this.updateData();
  };

  handleCloseDialogConfirm = () => {
    this.updateData();
  };

  handleOnSave = (e) => {
    const {registerModel} = this.state;
    registerValidator.hasChangedAll(true);
    const validate = registerValidator.validate(registerModel);
    if (validate.isValid === true) {
      AddOrganizationUnit(registerModel).then(() => {
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
      EditOrganizationUnit(editModel.id, editModel).then(() => {
        this.updateData();
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
      searchOrganizationUnits(0, this.state.rowsPerPage, value, this.state.searchBy).then(this.mapData).then((data) => this.setState({
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
      openDialogNoDelete,
      head
    } = this.state;

    const validateRegister = registerValidator.validate(registerModel);
    const validateEdit = editValidator.validate(editModel);

    return (<Layout history={this.props.history}>
      <div className={classes.container}>
        <Navigator>
          <SearchInput onInput={this.onFilter}/>
          <Typography color={'primary'} variant={'title'} className={classNames(classes.title)}>{t('org_units')}</Typography>
        </Navigator>
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t)} data={this.state.data} searching={this.state.searching}/>
        <BottomTools>
          <AddButton action={this.handleClickOpenDialogRegister} tooltip={t('add_ou')}/>
        </BottomTools>

        {/* OrganizationUnit can not be deleted */}
        <ModalDialog openModal={openDialogNoDelete} closeDialog={this.updateData} disableActionButtons={true}>
          <Typography align='center' variant='title'>
            {t('no_delete_ou')}
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
          }} openModal={openDialogRegister} openDialog={this.handleClickOpenDialog} closeDialog={this.handleCloseDialogRegister} title={t('ou_data')} saveAction={this.handleOnSave} textButton={t('save')}>
          <AddOrganizationUnits AddOrganizationUnits="AddOrganizationUnits" model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}></AddOrganizationUnits>
        </ModalDialog>

        {/* Edit modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.editModal
          }} openModal={openDialogEdit} openDialog={this.handleClickOpenDialogEdit} closeDialog={this.handleCloseDialogEdit} title={t('ou_edit')} saveAction={this.handleOnEdit} textButton={t('save')}>
          <EditOrganizationUnits EditOrganizationUnits="EditOrganizationUnits" model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}></EditOrganizationUnits>
        </ModalDialog>

        {/* Info modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.infoModal
          }} openModal={openDialogInfo} openDialog={this.handleClickOpenDialogInfo} closeDialog={this.handleCloseDialogInfo} disableActionButtons={true} title={t('info')}>
          <ViewOrganizationUnits ViewOrganizationUnits="ViewOrganizationUnits" model={infoModel}></ViewOrganizationUnits>
        </ModalDialog>
      </div>
    </Layout>);
  }
}
OrganizationUnits.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(OrganizationUnits));
