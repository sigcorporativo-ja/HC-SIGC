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
import DeviceHub from '@material-ui/icons/DeviceHub';
import Typography from '@material-ui/core/Typography';

import Layout from 'components/Layout';
import Navigator from 'components/Navigator';
import Results from 'components/Results';
import RowOptions from 'components/RowOptions';
import OptionButton from 'components/OptionButton';
import SearchInput from 'components/SearchInput';
import ModalDialog from 'components/ModalDialog';
import {hasPermissions} from 'utils/Securization';
import FormValidator from 'utils/FormValidator';
import EditDBConnections from 'components/form/dbconnection/EditDBConnection';
import ViewDBConnections from 'components/form/dbconnection/ViewDBConnection';

import {searchDBConnections, EditDBConnection, DeleteDBConnection, getDBConnectionById, getPaginateDBConnection} from 'controllers/DBConnectionController';

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
    marginLeft: '40rem'
  },
  infoModal: {
    height: '58vh',
    width: '40vh'
  },
  editModal: {
    height: '60vh',
    width: '40vh'
  },
  removeModal: {
    padding: '1rem'
  }
});

const head = (t) => (<TableRow>
  <TableCell></TableCell>
  <TableCell>{t('dbms')}</TableCell>
  <TableCell>{t('descriptive_name')}</TableCell>
  <TableCell>{t('host')}</TableCell>
  <TableCell>{t('database')}</TableCell>
  <TableCell>{t('schema')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const infoModel = {
  name: '',
  host: '',
  port: '',
  database: '',
  schema: '',
  user: '',
  dbms: {}
};

const editModel = {
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

const editValidator = new FormValidator([
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

class DBConnection extends Component {
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
      infoModel,
      editModel,
      optionsSelect: [],
      optionSelect: '',
      filter: '',
      searchBy: 'name,host,database,schema',
      suggestions: []
    };
    this.updateData();
  }

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((DBConnectionDTO) => {
      return (<TableRow hover={true} key={DBConnectionDTO.id}>
        <TableCell className={classNames(classes.cellIcon)}>
          <DeviceHub/>
        </TableCell>
        <TableCell>{DBConnectionDTO.dbms.name}</TableCell>
        <TableCell>{DBConnectionDTO.name}</TableCell>
        <TableCell>{DBConnectionDTO.host}</TableCell>
        <TableCell>{DBConnectionDTO.database}</TableCell>
        <TableCell>{DBConnectionDTO.schema}</TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={DBConnectionDTO.id} action={this.handleClickOpenDialogInfo} tooltip={t('see_data')}>
              <RemoveRedEye className={classNames(classes.eye)}/>
            </OptionButton>
            <OptionButton id={DBConnectionDTO.id} action={this.handleClickOpenDialogEdit} tooltip={t('edit_data')}>
              <Create className={classNames(classes.edit)}/>
            </OptionButton>
            <OptionButton id={DBConnectionDTO.id} action={this.handleClickOpenDialogConfirm} tooltip={t('delete_data')}>
              <Delete className={classNames(classes.delete)}/>
            </OptionButton>
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
      getPaginateDBConnection(start, size).then(this.mapData).then((data) => this.setState({
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
        infoModel,
        editModel
      }));
    } else {
      searchDBConnections(start, size, filter, this.state.searchBy).then(this.mapData).then((data) => this.setState({
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
        infoModel,
        editModel
      }));
    }

    this.translate(editValidator);
  }

  // Handle methods -----------------------------------------------------------

  handleChangePage = (event, page) => {
    this.renderData(page, this.state.rowsPerPage);
  };

  handleChangeRowsPerPage = event => {
    this.renderData(this.state.page, event.target.value);
  };

  handleOnDelete = () => {
    DeleteDBConnection(this.deleteId).then(() => {
      this.updateData();
    }).catch(err => {
      this.handleClickOpenDialogNoDelete();
    });
  };

  handleCloseDialog = () => {
    this.updateData();
  }

  handleOnEdit = (e) => {
    const {editModel} = this.state;
    editValidator.hasChangedAll(true);
    const validate = editValidator.validate(editModel);
    if (validate.isValid === true) {
      EditDBConnection(editModel.id, editModel).then(() => {
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
    getDBConnectionById(id).then((model) => {
      this.setState({
        ...this.state,
        infoModel: model,
        openDialogInfo: true
      });
    });
  };

  handleClickOpenDialogEdit = (id, evt) => {
    getDBConnectionById(id).then((model) => {
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
    editValidator.hasChangedAll(false);
    this.renderData(this.state.page, this.state.rowsPerPage);
  }

  formRegisterRefCallback = (instance) => {
    this.formRegister = instance;
  }

  formEditRefCallback = (instance) => {
    this.formEdit = instance;
  }

  handleOnChangeSelectSearchEdit = (event, {newValue}) => {
    const idSelect = newValue.id !== undefined
      ? newValue.id
      : 0;

    const nameSelect = newValue.name !== undefined
      ? newValue.name
      : newValue;

    if (newValue.length < 1) {
      this.setState({
        ...this.state,
        editModel: {
          ...this.state.editModel,
          dbms: {
            value: {
              name: nameSelect,
              id: idSelect
            }
          }
        }
      });
    } else {
      this.setState({
        ...this.state,
        editModel: {
          ...this.state.editModel,
          dbms: {
            value: {
              id: idSelect,
              name: nameSelect
            }
          }
        },
        suggestions: this.getSuggestions(nameSelect)
      });
    }
  }

  handleOnChangeSelectSearchRegister = (event, {newValue}) => {
    const idSelect = newValue.id !== undefined
      ? newValue
      : 0;

    const nameSelect = newValue.name !== undefined
      ? newValue.name
      : newValue;

    if (newValue.length < 1) {
      this.setState({
        ...this.state,
        registerModel: {
          ...this.state.registerModel,
          dbms: {
            value: {
              name: nameSelect,
              id: idSelect
            }
          }
        }
      });
    } else {
      this.setState({
        ...this.state,
        registerModel: {
          ...this.state.registerModel,
          dbms: {
            value: {
              id: idSelect,
              name: nameSelect
            }
          }
        },
        suggestions: this.getSuggestions(nameSelect)
      });
    }
  }

  onSuggestionsFetchRequested = ({value}) => {}

  getSuggestions = (value) => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.state.optionsSelect.filter(lang => lang.label.toLowerCase().includes(inputValue));
  };

  getSuggestionValue = (suggestion) => {
    const id = suggestion.value;
    const name = suggestion.label;
    const result = {
      id,
      name
    };
    return result;
  };

  onSuggestionsClearRequested = () => {
    this.setState({suggestions: []});
  };

  onFilter = (e) => {
    const value = e.target.value;
    if (value.trim() !== '') {
      this.setState({
        ...this.state,
        searching: true
      })
      searchDBConnections(0, this.state.rowsPerPage, value, this.state.searchBy).then(this.mapData).then((data) => this.setState({
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
    let {editModel} = this.state;
    const {infoModel, openDialogInfo, openDialogEdit, openDialogConfirm, openDialogNoDelete} = this.state;

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
        <Navigator >
          <SearchInput onInput={this.onFilter}/>
          <Typography variant={'title'} color={'primary'} className={classNames(classes.title)}>{t('dbconnection')}</Typography>
        </Navigator>
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t)} data={this.state.data} searching={this.state.searching}/> {/* Delete modal */}

        <ModalDialog inheritedClasses={{
            containerContent: classes.removeModal
          }} openModal={openDialogConfirm} closeDialog={this.handleCloseDialogConfirm} textButton={t('confirm')} saveAction={this.handleOnDelete}>
          <Typography align='center' variant='title'>
            {t('ask_confirm')}
          </Typography>
        </ModalDialog>

        {/* DBConnection can not be deleted */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.removeModal
          }} openModal={openDialogNoDelete} closeDialog={this.updateData} disableActionButtons={true}>
          <Typography align='center' variant='title'>
            {t('no_delete_dbConnection')}
          </Typography>
        </ModalDialog>

        {/* Edit modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.editModal
          }} openModal={openDialogEdit} closeDialog={this.handleCloseDialogEdit} title={t('dbconnection_edit')} saveAction={this.handleOnEdit} textButton={t('save')}>
          <EditDBConnections EditDBConnections="EditDBConnections" model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}></EditDBConnections>
        </ModalDialog>

        {/* Info modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.infoModal
          }} openModal={openDialogInfo} closeDialog={this.handleCloseDialogInfo} disableActionButtons={true} title={t('dbconnection_info')}>
          <ViewDBConnections model={infoModel}/>
        </ModalDialog>
      </div>
    </Layout>);
  }
}
DBConnection.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(DBConnection));
