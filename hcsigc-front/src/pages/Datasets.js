import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import {withStyles} from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';

import classNames from 'classnames';
import OpenIcon from '@material-ui/icons/OpenInNew';
import RemoveRedEye from '@material-ui/icons/RemoveRedEye';
import GradientIcon from '@material-ui/icons/Gradient';
import Info from '@material-ui/icons/Info';
import Create from '@material-ui/icons/Create';
import Delete from '@material-ui/icons/Delete';
import LockCloseIcon from '@material-ui/icons/Lock';
import LockOpenIcon from '@material-ui/icons/LockOpen';
import StarBorder from '@material-ui/icons/StarBorder';
import GridIcon from '@material-ui/icons/GridOn';
import LayerIcon from '@material-ui/icons/Layers';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';

import Layout from 'components/Layout';
import Navigator from 'components/Navigator';
import Results from 'components/Results';
import BottomTools from 'components/BottomTools';
import AddButton from 'components/AddButton';
import RowOptions from 'components/RowOptions';
import OptionButton from 'components/OptionButton';
import VisibilityFilters from 'components/VisibilityFilters';
import CalendarButton from 'components/CalendarButton';
import SearchInput from 'components/SearchInput';
import ModalDialog from 'components/ModalDialog';
import SecurizedComponent from 'components/SecurizedComponent';
import FormValidator from 'utils/FormValidator';
import AddDatasets from 'components/form/dataset/AddDatasets';
import EditDatasets from 'components/form/dataset/EditDatasets';
import ViewDatasets from 'components/form/dataset/ViewDatasets';
import OrderDateDatasets from 'components/form/dataset/OrderDateDatasets';
import AddGroupsToShare from 'components/form/dataset/AddGroupsToShare';

import Schedule from '@material-ui/icons/Schedule';
import Star from '@material-ui/icons/Star';
import SortSection from 'components/SortSection';
import Checkbox from '@material-ui/core/Checkbox';
import Tooltip from '@material-ui/core/Tooltip';
import PersonIcon from '@material-ui/icons/Person';
import GroupIcon from '@material-ui/icons/Group';
import PublicIcon from '@material-ui/icons/Public';

import {hasPermissions} from 'utils/Securization';

import {
  searchDatasets,
  getPaginateDatasets,
  getDatasetById,
  EditDataset,
  DeleteDataset,
  AddDataset,
  InfoDataset,
  DeleteBatchDataUpload,
  DeleteDataUpload
} from 'controllers/DatasetController';
import {getUserLogged} from 'controllers/UserController';
import {getGroupsToShare} from 'controllers/GroupController'
import {getUserSession} from 'controllers/AuthController';

const styles = theme => ({
  palette: 'primary',
  star: {
    color: '#F3DA0B'
  },
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
  lockClose: {
    color: '#c62828',
    marginTop: '11px'
  },
  lockOpen: {
    color: '#2E7D32',
    marginTop: '11px'
  },

  helper: {
    marginTop: '3rem',
    color: '#f44336'
  },
  sort: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
    marginRight: '10.2rem',
    marginBottom: '-1.6rem'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  },
  button: {
    color: "#ffffff",
    backgroundColor: '#1C74A6'
  },
  icon: {
    color: '#01579b',
    alignSelf: 'center',
    marginRight: '15px'
  },
  title: {
    marginTop: '1.9rem',
    marginLeft: '13rem'
  },
  confirmRemove: {
    padding: '1rem'
  },
  addDataset: {
    height: '40vh'
  },
  orderData: {
    height: '20vh'
  },
  containerFavInfo: {
    display: 'flex',
    justifyContent: 'space-around'
  }
});

const head = (t, classes) => (<TableRow>
  <TableCell>
    <Grid container={true} spacing={0}>
      {
        getUserSession()
          ? <Grid item={true} sm={3}>
              {t('fav')}
            </Grid>
          : null
      }
      <Grid item={true} sm={8}>
        {t('info')}
      </Grid>
    </Grid>
  </TableCell>
  <SecurizedComponent permissions={['ADMIN_UO']}>
    <TableCell>{t('org_unit')}</TableCell>
  </SecurizedComponent>
  <SecurizedComponent permissions={['SHARE_DATA']}>
    <TableCell>{t('me_author')}</TableCell>
  </SecurizedComponent>
  <TableCell>{t('name')}</TableCell>
  <TableCell>{t('options')}</TableCell>
</TableRow>)

const registerModel = {
  name: '',
  description: '',
  global: false,
  associatedFile: ''
};
const infoModel = {
  name: '',
  description: '',
  tableName: '',
  global: false
};
const editModel = {
  name: '',
  description: '',
  tableName: '',
  global: false,
  groups: []
};
const orderDateModel = {
  startDate: '',
  endDate: ''
}

const dateValidator = new FormValidator([
  {
    field: 'startDate',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'endDate',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }, {
    field: 'endDate',
    method: 'dateLTE',
    validWhen: false,
    message: 'valid_date'
  }
])
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
  }, {
    field: 'associatedFile',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_file'
  }, {
    field: 'name',
    method: 'noStartNumber',
    validWhen: false,
    message: 'no_start_number'
  }, {
    field: 'name',
    method: 'noSpecialCharacter',
    validWhen: false,
    message: 'no_special_character'
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
  }, {
    field: 'tableName',
    method: 'isEmpty',
    validWhen: false,
    message: 'required_field'
  }
]);
class DataSets extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      groupsToShare: [],
      page: 0,
      rowsPerPage: 5,
      count: 0,
      registerModel,
      searching: true,
      infoModel,
      editModel,
      orderDateModel,
      openDialogRegister: false,
      openDialogInfo: false,
      openDialogEdit: false,
      openDialogConfirm: false,
      openDialogOrderDate: false,
      openDialogConfirmRegister: false,
      filter: '',
      searchBy: 'name,description',
      checks: 'private',
      orderName: '',
      userLogged: '',
      dataUploadId: '',
      datasetId: ''
    };

    getUserLogged().then((userLogged) => {
      this.setState({
        ...this.state,
        userLogged
      })
    });

    if (hasPermissions(['SHARE_DATA'])) {
      getGroupsToShare().then((groupsToShare) => {
        this.setState({
          ...this.state,
          groupsToShare
        })
      });
    }

    this.updateData(false);
  }

  renderData = (page, rowsPerPage) => {
    const {filter, checks, searchBy, orderName, orderDateModel} = this.state;
    const {startDate, endDate} = orderDateModel;
    const start = (page) * rowsPerPage;
    const size = rowsPerPage;
    if (filter === '') {
      getPaginateDatasets(start, size, checks, orderName, startDate, endDate).then(this.mapData).then((data) => this.setState({
        count: data.count,
        page,
        rowsPerPage,
        registerModel,
        searching: false,
        infoModel,
        editModel,
        orderDateModel,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openDialogOrderDate: false,
        openDialogConfirmRegister: false,
        data: data.data,
        helper: ''
      }));
    } else {
      searchDatasets(start, size, filter, searchBy, checks, orderName, startDate, endDate).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page,
        rowsPerPage,
        registerModel,
        infoModel,
        editModel,
        orderDateModel,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openDialogOrderDate: false,
        openDialogConfirmRegister: false,
        count: data.count,
        data: data.data
      }));
    }

    this.translate(registerValidator);
    this.translate(editValidator);
  }

  updateData = () => {
    registerValidator.hasChangedAll(false);
    editValidator.hasChangedAll(false);
    dateValidator.hasChangedAll(false);
    this.renderData(this.state.page, this.state.rowsPerPage);
  };

  formRegisterRefCallback = (instance) => {
    this.formRegister = instance;
  }

  formEditRefCallback = (instance) => {
    this.formEdit = instance;
  }

  handleChangePage = (event, page) => {
    this.renderData(page, this.state.rowsPerPage);
  };

  handleChangeRowsPerPage = event => {
    this.renderData(this.state.page, event.target.value);
  };

  handleOnSave = (e) => {
    let {registerModel} = this.state;
    registerValidator.hasChangedAll(true);
    registerModel = {
      ...registerModel,
      associatedFile: (document.getElementById('datasetFile').files[0] !== undefined)
        ? document.getElementById('datasetFile').files[0].name
        : ''
    }
    const validate = registerValidator.validate(registerModel);
    if (validate.isValid === true) {
      AddDataset(registerModel, 'datasetFile', (e) => console.log(e)).then((response) => {
        const ids = response.request.response.split(",");
        const dataUploadId = ids[0].trim().replace("dataUploadId=", "");
        const datasetId = ids[1].trim().replace("datasetId=", "");
        this.setState({
          ...this.state,
          dataUploadId,
          datasetId
        });
        this.handleOpenConfirmRegister();
      }).catch((error) => alert(error));
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleOnSaveConfirmRegister = () => {
    const {dataUploadId} = this.state;
    DeleteBatchDataUpload(dataUploadId).then(() => {
      this.updateData();
    })
  }

  handleOnEdit = (e) => {
    const {editModel} = this.state;
    editValidator.hasChangedAll(true);
    const validate = editValidator.validate(editModel);
    if (validate.isValid === true) {
      EditDataset(editModel.id, editModel).then(() => {
        this.updateData();
      });
    } else {
      this.setState({
        ...this.state
      });
    }
  };

  handleOnDelete = () => {
    DeleteDataset(this.deleteId).then(() => {
      this.updateData();
    });
  };

  handlerOnChangeEdit = (e) => {
    editValidator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      editModel: {
        ...this.state.editModel,
        [e.target.name]: e.target.name === 'global'
          ? e.target.checked
          : e.target.value
      }
    });
  };

  handleOpenConfirmRegister = () => {
    this.setState({openDialogConfirmRegister: true})
  }

  handleClickOpenDialogConfirm = (id) => {
    this.deleteId = id;
    this.setState({openDialogConfirm: true});
  };

  handleClickOpenDialogRegister = () => {
    this.setState({openDialogRegister: true});
  };

  handleClickOpenDialogOrderDate = () => {
    this.setState({
      ...this.state,
      openDialogOrderDate: true
    });
  };

  handlerOnChangeRegister = (e) => {
    registerValidator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      registerModel: {
        ...this.state.registerModel,
        [e.target.name]: e.target.name === 'global'
          ? e.target.checked
          : e.target.value
      }
    });
  };

  handleClickOpenInfoDialog = (id, evt) => {
    getDatasetById(id).then((model) => {
      this.setState({
        ...this.state,
        infoModel: model,
        openDialogInfo: true
      });
    });
  };

  handleClickOpenEditDialog = (id, evt) => {
    getDatasetById(id).then((model) => {
      this.setState({
        ...this.state,
        editModel: model,
        openDialogEdit: true
      });
    });
  };

  handleCloseDialogRegister = () => {
    this.updateData();
  };
  handleCloseDialogEdit = () => {
    this.updateData();
  };
  handleCloseDialogInfo = () => {
    this.updateData();
  };
  handleCloseDialogConfirm = () => {
    this.updateData();
  };

  handleOnCloseDialogOrdeDate = () => {
    this.setState({
      ...this.state,
      openDialogOrderDate: false,
      ...this.state.orderDateModel,
      orderDateModel: {
        startDate: '',
        endDate: ''
      }
    })
  }

  handleCloseDialogConfirmRegister = () => {
    const {dataUploadId, datasetId} = this.state;
    DeleteDataUpload(dataUploadId).then(() => {
      DeleteBatchDataUpload(dataUploadId).then(() => {
        DeleteDataset(datasetId).then(() => {
          this.setState({
            ...this.state,
            openDialogConfirmRegister: false
          })
        })
      })
    })
  };

  mapData = (data) => {
    const {t, classes} = this.props;
    const mappedData = data.data.map((datasetDTO) => {

      return (<TableRow hover={true} key={datasetDTO.id}>
        <TableCell>
          <RowOptions>
            {
              getUserSession()
                ? <Tooltip title={t('add_fav')} classes={{
                      tooltip: classes.lightTooltip
                    }}>
                    <Checkbox icon={<StarBorder className = {
                        classNames(this.props.classes.star)
                      } />} checkedIcon={<Star className = {
                        classNames(this.props.classes.star)
                      } />}/>
                  </Tooltip>
                : null
            }
            {this.showType(datasetDTO)}
            {this.showInfo(datasetDTO)}
          </RowOptions>
        </TableCell>
        <SecurizedComponent permissions={['ADMIN_UO']}>
          <TableCell>{datasetDTO.user.org_unit.name}</TableCell>
        </SecurizedComponent>
        <SecurizedComponent permissions={['SHARE_DATA']}>
          <TableCell>
            {datasetDTO.user.username}
          </TableCell>
        </SecurizedComponent>
        <TableCell>
          {datasetDTO.name}
        </TableCell>
        <TableCell>
          <RowOptions>
            <OptionButton id={datasetDTO.id} action={InfoDataset} tooltip={t('see_data')}>
              <OpenIcon className={classNames(this.props.classes.eye)}/>
            </OptionButton>
            <OptionButton id={datasetDTO.id} action={this.handleClickOpenInfoDialog} tooltip={t('see_info')}>
              <Info className={classNames(this.props.classes.info)}/>
            </OptionButton>
            {this.showEditDelete(datasetDTO, t)}
          </RowOptions>
        </TableCell>
      </TableRow>);
    });
    return {data: mappedData, count: data.count};
  };

  showType = (datasetDTO) => {
    const {t, classes} = this.props;
    let iconType;
    if (datasetDTO.dataType.name === "TABLE") {
      iconType = <Tooltip title={t('table')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <GridIcon className={classNames(classes.icon)}/>
      </Tooltip>
    }
    if (datasetDTO.dataType.name === "LAYER") {
      iconType = <Tooltip title={t('layer')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <LayerIcon className={classNames(classes.icon)}/>
      </Tooltip>
    }
    if (datasetDTO.dataType.name === "RASTER") {
      iconType = <Tooltip title={'Raster'} classes={{
          tooltip: classes.lightTooltip
        }}>
        <GradientIcon className={classNames(classes.icon)}/>
      </Tooltip>
    }
    return iconType;
  }

  showInfo = (datasetDTO) => {
    const {t, classes} = this.props;
    const {userLogged} = this.state;
    let person;
    let group;
    let l_global;
    let sharedIcon = <Tooltip title={t('not_shared')} classes={{
        tooltip: classes.lightTooltip
      }}>
      <LockCloseIcon className={classNames(classes.lockClose)}/>
    </Tooltip>
    if (userLogged.id.value === datasetDTO.user.id) {
      person = <Tooltip title={t('author')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <PersonIcon className={classNames(classes.icon)}/>
      </Tooltip>
    }

    const isShared = datasetDTO.shareToUsers.some(shareToUser => shareToUser.id === userLogged.id.value);
    const allShared = datasetDTO.shareToUsers;
    if (isShared && userLogged.id.value !== datasetDTO.user.id) {
      group = <Tooltip title={t('from_my_groups')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <GroupIcon className={classNames(classes.icon)}/>
      </Tooltip>
    }
    if (allShared.length > 0) {
      sharedIcon = <Tooltip title={t('shared')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <LockOpenIcon className={classNames(classes.lockOpen)}/>
      </Tooltip>
    }
    if (datasetDTO.global) {
      l_global = <Tooltip title={t('global')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <PublicIcon className={classNames(classes.icon)}/>
      </Tooltip>
      sharedIcon = '';
    }

    return (<React.Fragment>
      <RowOptions>
        {person}
        {group}
        {l_global}
        {
          userLogged.id.value === datasetDTO.user.id
            ? sharedIcon
            : null
        }
      </RowOptions>
    </React.Fragment>);
  }

  showEditDelete = (datasetDTO, t) => {
    if (!datasetDTO.global && this.state.userLogged.id.value === datasetDTO.user.id) {
      return (<SecurizedComponent permissions={['SHARE_DATA']}>
        {this.continueShowEditDelete(datasetDTO, t)}
      </SecurizedComponent>)
    } else {
      return (<SecurizedComponent permissions={['ADMIN_UO']}>
        {this.continueShowEditDelete(datasetDTO, t)}
      </SecurizedComponent>)
    }
  };

  continueShowEditDelete = (datasetDTO, t) => {
    return (<RowOptions>
      <OptionButton id={datasetDTO.id} action={this.handleClickOpenEditDialog} tooltip={t('edit_data')}>
        <Create className={classNames(this.props.classes.edit)}/>
      </OptionButton>
      <OptionButton id={datasetDTO.id} action={this.handleClickOpenDialogConfirm} tooltip={t('delete_data')}>
        <Delete className={classNames(this.props.classes.delete)}/>
      </OptionButton>
    </RowOptions>)
  }

  onFilter = (e) => {
    const value = e.target.value;
    const {searchBy, checks, orderName, rowsPerPage, orderDateModel} = this.state;
    const {startDate, endDate} = orderDateModel;
    if (value.trim() !== '') {
      this.setState({
        ...this.state,
        searching: true
      })
      searchDatasets(0, rowsPerPage, value, searchBy, checks, orderName, startDate, endDate).then(this.mapData).then((data) => this.setState({
        ...this.state,
        page: 0,
        searching: false,
        registerModel,
        infoModel,
        editModel,
        orderDateModel,
        openDialogRegister: false,
        openDialogInfo: false,
        openDialogEdit: false,
        openDialogConfirm: false,
        openDialogOrderDate: false,
        openDialogConfirmRegister: false,
        count: data.count,
        data: data.data,
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

  handleOnSort = (evt, id, name) => {
    const {orderDateModel, checks, rowsPerPage} = this.state;
    const {startDate, endDate} = orderDateModel;
    const orderName = name;
    getPaginateDatasets(0, rowsPerPage, checks, orderName, startDate, endDate).then(this.mapData).then((data) => {
      this.setState({
        count: data.count,
        page: 0,
        registerModel,
        openDialogRegister: false,
        data: data.data,
        checks,
        orderName
      });
    });
  };

  handlerOnChangeDateOrder = (e) => {
    dateValidator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      orderDateModel: {
        ...this.state.orderDateModel,
        [e.target.name]: e.target.value
      }
    });
  }

  handlerOrderDate = (event) => {
    const {orderDateModel, checks, rowsPerPage, orderName} = this.state;
    const {startDate, endDate} = orderDateModel;
    dateValidator.hasChangedAll(true);
    const validate = dateValidator.validate(orderDateModel);
    if (validate.isValid) {
      getPaginateDatasets(0, rowsPerPage, checks, orderName, startDate, endDate).then(this.mapData).then((data) => {
        this.setState({
          count: data.count,
          page: 0,
          registerModel,
          openDialogRegister: false,
          data: data.data,
          checks,
          orderName,
          openDialogOrderDate: false
        });
      });
    } else {
      this.forceUpdate();
    }
  }

  onCheck = (e) => {
    const {rowsPerPage, orderName, orderDateModel} = this.state;
    const {startDate, endDate} = orderDateModel;
    let checks;

    if (e.target.checked === true) {
      checks = (this.state.checks === '')
        ? e.target.value
        : this.state.checks + ',' + e.target.value
    } else {
      checks = this.state.checks.replace(e.target.value, '').replace(',,', ',');
      if (checks.substr(0, 1) === ',') 
        checks = checks.substr(1, checks.length); //Quito comas si las hay al comienzo
      if (checks.substr(checks.length - 1, checks.length) === ',') 
        checks = checks.substr(0, checks.length - 1); //Quito comas si las hay al final
      }
    getPaginateDatasets(0, rowsPerPage, checks, orderName, startDate, endDate).then(this.mapData).then((data) => this.setState({
      count: data.count,
      page: 0,
      registerModel,
      openDialogRegister: false,
      data: data.data,
      checks
    }));
  }

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
      orderDateModel,
      openDialogRegister,
      openDialogInfo,
      openDialogEdit,
      openDialogConfirm,
      groupsToShare,
      openDialogOrderDate,
      openDialogConfirmRegister
    } = this.state;

    const validateRegister = registerValidator.validate(registerModel);
    const validateEdit = editValidator.validate(editModel);
    const validateOrderDate = dateValidator.validate(orderDateModel);

    return (<Layout history={this.props.history}>
      <div className={classes.container}>
        <Navigator>
          <SearchInput onInput={this.onFilter}/>
          <CalendarButton action={this.handleClickOpenDialogOrderDate}/>
          <SecurizedComponent permissions={['SHARE_DATA']}>
            <VisibilityFilters onCheck={this.onCheck} initialChecked={this.state.checks}/>
          </SecurizedComponent>
          <Typography variant={'title'} color={'primary'} className={classNames(classes.title)}>{t('dataset')}
          </Typography>
        </Navigator>
        <div className={classNames(classes.sort)}>
          <SortSection>
            <OptionButton tooltip={t('order_fav')} action={this.handleOnSort} placeTooltip='top-start'>
              <Star className={classNames(classes.star)}/>
            </OptionButton>
            <OptionButton tooltip={t('order_seen')} action={this.handleOnSort} placeTooltip='top-start'>
              <RemoveRedEye className={classNames(classes.eye)}/>
            </OptionButton>
            <OptionButton name='d_creation_date' tooltip={t('order_create')} action={this.handleOnSort} placeTooltip='top-start'>
              <Schedule className={classNames(classes.info)}/>
            </OptionButton>
          </SortSection>
        </div>
        <Results state={this.state} handleChangePage={this.handleChangePage} handleChangeRowsPerPage={this.handleChangeRowsPerPage} head={head(t, classes)} data={this.state.data} searching={this.state.searching}/>
        <SecurizedComponent permissions={['SHARE_DATA']}>
          <BottomTools>
            <AddButton action={this.handleClickOpenDialogRegister} tooltip={t('add_data')}/>
          </BottomTools>
        </SecurizedComponent>

        {/* Add modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.addDataset
          }} openModal={openDialogRegister} openDialog={this.handleClickOpenDialogRegister} closeDialog={this.handleCloseDialogRegister} title={t('dataset')} saveAction={this.handleOnSave} textButton={t('send')}>
          <AddDatasets model={registerModel} onChange={this.handlerOnChangeRegister} validator={validateRegister}/>
        </ModalDialog>

        {/* Edit modal */}
        <ModalDialog openModal={openDialogEdit} openDialog={this.handleClickOpenEditDialog} closeDialog={this.handleCloseDialogEdit} title={t('dataset_edit')} saveAction={this.handleOnEdit} textButton={t('save')} childrenComponents={[
            {
              label: t('info'),
              component: (<EditDatasets model={editModel} onChange={this.handlerOnChangeEdit} validator={validateEdit}/>)
            }, {
              label: t('share_data'),
              component: (<AddGroupsToShare model={editModel} groupsToShare={groupsToShare} onChange={this.handlerOnChangeEdit} validator={validateEdit}/>)
            }
          ]}/> {/* Info modal */}
        <ModalDialog openModal={openDialogInfo} openDialog={this.handleClickOpenDialogInfo} closeDialog={this.handleCloseDialogInfo} disableActionButtons={true} title={t('info')}>
          <ViewDatasets model={infoModel}></ViewDatasets>
        </ModalDialog>

        {/* Delete modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.confirmRemove
          }} openModal={openDialogConfirm} closeDialog={this.handleCloseDialogConfirm} textButton={t('confirm')} saveAction={this.handleOnDelete}>
          <Typography align='center' variant='title'>
            {t('ask_confirm')}
          </Typography>
        </ModalDialog>

        {/* Order Date Modal */}
        <ModalDialog inheritedClasses={{
            containerContent: classes.orderData
          }} openModal={openDialogOrderDate} closeDialog={this.handleOnCloseDialogOrdeDate} textButton={t('confirm')} saveAction={this.handlerOrderDate} title={t('calendar')}>
          <OrderDateDatasets OrderDateDatasets="OrderDateDatasets" model={orderDateModel} onChange={this.handlerOnChangeDateOrder} validator={validateOrderDate}/>
        </ModalDialog>

        {/* Confirm dataset register */}
        <ModalDialog openModal={openDialogConfirmRegister} closeDialog={this.handleCloseDialogConfirmRegister} title={t('dataset')} saveAction={this.handleOnSaveConfirmRegister} textButton={t('save')}>
          <Typography align='center' variant='title'>
            {t('confirm_register_dataset')}
          </Typography>
        </ModalDialog>

      </div>
    </Layout>);

  }
}

DataSets.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(DataSets));
