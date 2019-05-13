import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import {withRouter} from 'react-router-dom';

import AppBar from '@material-ui/core/AppBar';
import Typography from '@material-ui/core/Typography';

import MenuOptions from 'components/MenuOptions';
import AdminPanel from 'components/AdminPanel';
import LoginOption from 'components/LoginOption';
import ModalDialog from 'components/ModalDialog';
import Login from 'components/form/login/Login';
import imgBar from 'static/fonts/icons/logo-horizontal-isotipo-HCSIGC-CHAP.svg';
import FormValidator from 'utils/FormValidator';

import {login, logout} from 'controllers/AuthController';

const styles = theme => ({
  containerBar: {
    padding: '5px 0px',
    display: 'flex',
    justifyContent: 'space-between',
    color: '#fff'
  },
  containerMenu: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end'
  },
  containerHeader: {
    display: 'flex',
    alignItems: 'center'
  },
  img: {
    height: '50px'
  },
  labelClaim: {
    width: 235
  },
  logoTitle: {
    display: 'flex',
    flexDirection: 'column',
    color: '#fff'
  },
  root: {
    borderBottom: '1px solid ' + theme.palette.primary.main
  },
  loginModal: {
    height: '20vh'
  }
});

class HCBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      openDialog: false,
      model: {
        username: '',
        password: ''
      },
      user: null,
      validator: new FormValidator([
        {
          field: 'username',
          method: 'isEmpty',
          validWhen: false,
          message: 'required_field'
        }, {
          field: 'password',
          method: 'isEmpty',
          validWhen: false,
          message: 'required_field'
        }
      ]),
      validate: null
    };
    this.translate()
  }

  handleClickOpenDialog = () => {
    this.setState({openDialog: true});
  };

  handleCloseDialog = () => {
    const {validator} = this.state;
    validator.hasChangedAll(false);
    this.setState({
      ...this.state,
      model: {
        username: '',
        password: ''
      },
      validate: null,
      openDialog: false
    });
  };

  handlerOnChange = (e) => {
    const {validator, model} = this.state;
    validator.hasChanged(e.target.name);
    this.setState({
      ...this.state,
      validate: true,
      validator,
      model: {
        ...model,
        [e.target.name]: e.target.value
      }
    });
  };

  handleOnLogin = (e) => {
    const {validator, model} = this.state;
    validator.hasChangedAll(true);
    const validate = validator.validate(model);
    if (validate.isValid === true) {
      login(model).then(() => {
        this.handleCloseDialog();
        this.reloadPage();
      }).catch((err) => alert(err));
    } else {
      this.setState({
        ...this.state,
        validate: true,
        validator
      });
    }
  }

  reloadPage = () => {
    const current = this.props.location.pathname;
    this.props.history.replace('/reload');
    setTimeout(() => {
      this.props.history.replace(current);
    });
  }

  handleOnLoginKeyPress = (e) => {
    if (e.key === 'Enter') {
      this.handleOnLogin(e);
    }
  }

  handleOnLoginClick = (e) => {
    this.handleOnLogin(e);
  }

  handleOnLogout = (e) => {
    logout().then(() => this.props.history.push('/main')).catch((err) => alert(err));
  };

  translate() {
    const {validator} = this.state;
    const {t} = this.props;
    validator.validations.map(m => m.message = t(m.message));
  }

  render() {
    const {classes, t} = this.props;
    const {validator, validate, model} = this.state;
    const _validate = validate === null
      ? validator.valid()
      : validator.validate(model);

    return (<AppBar className={classNames(classes.root)}>
      <div className={classes.containerBar}>
        <div className={classes.containerHeader}>
          <AdminPanel permissions={[
              'ADMIN_CONFIG',
              'ADMIN_DATABASES',
              'ADMIN_GROUP',
              'ADMIN_GROUP_USERS',
              'ADMIN_UO',
              'ADMIN_USERS'
            ]}/>
          <img src={imgBar} className={classes.img} alt=''/>
          <div className={classes.logoTitle}>
            <Typography variant={'title'} color={'secondary'}>SIGC</Typography>
            <Typography color={'secondary'} className={classNames(classes.labelClaim)}>Herramienta Centralizada del SIGC Corporativo de la Junta de Andalucía</Typography>
          </div>
        </div>
        <div className={classes.containerMenu}>
          <MenuOptions flexDirection={'row'}/>
          <LoginOption openDialog={this.handleClickOpenDialog} onLogout={this.handleOnLogout}></LoginOption>
          <ModalDialog inheritedClasses={{
              containerContent: classes.loginModal
            }} openModal={this.state.openDialog} openDialog={this.handleClickOpenDialog} closeDialog={this.handleCloseDialog} title={t('login')} saveAction={this.handleOnLoginClick} textButton={t('access')}>
            <Login model={model} onChange={this.handlerOnChange} validator={_validate} onKeyPress={this.handleOnLoginKeyPress}/>
          </ModalDialog>
        </div>
      </div>
    </AppBar>);
  }
}
HCBar.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};
export default translate()(withRouter(withStyles(styles, {withTheme: true})(HCBar)));
