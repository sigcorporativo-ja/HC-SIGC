import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import {withStyles} from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import MemoryIcon from '@material-ui/icons/Memory';
import classNames from 'classnames';
import Create from '@material-ui/icons/Create';

import {hasPermissions} from 'utils/Securization';
import Layout from 'components/Layout';
import Navigator from 'components/Navigator';
import Results from 'components/Results';
import RowOptions from 'components/RowOptions';
import OptionButton from 'components/OptionButton';
import ModalDialog from 'components/ModalDialog';
import SearchInput from 'components/SearchInput';
import EditConfiguration from 'components/form/configuration/EditConfiguration';
import FormValidator from 'utils/FormValidator';

import {searchConfigurations, getPaginateConfiguration, getConfigurationById, editConfiguration} from 'controllers/ConfigurationController';

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
  editModal: {
    height: '25vh',
    width: '40vh'
  }
});

const head = (t) => (<TableRow>
  <TableCell></TableCell>
  <TableCell>{t('key')}</TableCell>
  <TableCell>{t('value')}</TableCell>
  <TableCell>{t('description')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const editModel = {
  key: '',
  description: '',
  value: ''
};

const editValidator = new FormValidator([
  {
    field: 'key',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'description',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'value',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
])

class Configuration extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      openDialogEdit: false,
      editModel,
      page: 0,
      rowsPerPage: 5,
      count: 0,
      searching: true,
      filter: '',
      searchBy: 'key,description,value'
    };
    this.updateData();
  }

  componentWillMount = () => {
    if (!hasPermissions(['ADMIN_CONFIG'])) {
      this.props.history.replace('/');
    }
  };

  renderData = (page, rowsPerPage) => {
    const {filter} = this.state;
    const start = (page) * rowsPerPage;
    const size = rowsPerPage;
    if (filter === '') {
      getPaginateConfiguration(start, size).then(this.mapData).then((data) => this.setState({
        ...this.state,
        count: data.count,
        searching: false,
        openDialogEdit: false,
        editModel,
        page,
        rowsPerPage,
        data: data.data
      }));
    } else {
      searchConfigurations(start, size, filter, this.state.searchBy).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page,
        rowsPerPage,
        count: data.count,
        data: data.data,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false
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

  handleOnEdit = (e) => {
    const {editModel} = this.state;
    editValidator.hasChangedAll(true);
    const validate = editValidator.validate(editModel);
    if (validate.isValid === true) {
      editConfiguration(editModel.id, editModel).then(() => {
        this.updateData();
      });
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleClickOpenDialogEdit = (id, evt) => {
    getConfigurationById(id).then((model) => {
      this.setState({
        ...this.state,
        editModel: model,
        openDialogEdit: true
      });
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

  handleCloseDialogEdit = () => {
    this.updateData();
  };

  // More methods -------------------------------------------------------------

  updateData = () => {
    editValidator.hasChangedAll(false);
    this.renderData(this.state.page, this.state.rowsPerPage);
  }

  formEditRefCallback = (instance) => {
    this.formEdit = instance;
  }

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((ConfigurationDTO) => {
      return (<TableRow hover={true} key={ConfigurationDTO.id}>
        <TableCell className={classNames(classes.cellIcon)}>
          <MemoryIcon/>
        </TableCell>
        <TableCell>{ConfigurationDTO.key}</TableCell>
        <TableCell>{ConfigurationDTO.value}</TableCell>
        <TableCell>{ConfigurationDTO.description}</TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={ConfigurationDTO.id} action={this.handleClickOpenDialogEdit} tooltip={t('edit_data')}>
              <Create className={classNames(classes.edit)}/>
            </OptionButton>
          </RowOptions>
        </TableCell>
      </TableRow>);
    });

    return {data: mappedData, count: data.count};
  };

  onFilter = (e) => {
    const value = e.target.value;
    if (value.trim() !== '') {
      this.setState({
        ...this.state,
        searching: true
      })
      searchConfigurations(0, this.state.rowsPerPage, value, this.state.searchBy).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page: 0,
        count: data.count,
        data: data.data,
        searching: false,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
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
    const {editModel, openDialogEdit} = this.state;
    const validateEdit = editValidator.validate(editModel);
    return (<Layout history={this.props.history}>
      <div className={classes.container}>
        <Navigator>
          <SearchInput onInput={this.onFilter}/>
          <Typography color={'primary'} variant={'title'} className={classNames(classes.title)}>{t('configuration')}</Typography>
        </Navigator>
        {/* Edit modal */}
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t)} data={this.state.data} searching={this.state.searching}/>
        <ModalDialog inheritedClasses={{
            containerContent: classes.editModal
          }} openModal={openDialogEdit} closeDialog={this.handleCloseDialogEdit} title={t('configuration_edit')} saveAction={this.handleOnEdit} textButton={t('save')}>
          <EditConfiguration EditConfiguration="EditConfiguration" model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}></EditConfiguration>
        </ModalDialog>
      </div>
    </Layout>);
  }
}

Configuration.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(Configuration));
