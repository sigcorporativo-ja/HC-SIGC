import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import classNames from 'classnames';
import {translate} from 'react-i18next';
import {Link} from 'react-router-dom';

import imgMain from 'static/fonts/icons/logo-circulo.svg';

import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
  login: {
    padding: '2rem',
    display: 'flex',
    flexGrow: 1,
    justifyContent: 'center'
  },
  loginWrapper: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center'
  },
  loginName: {
    margin: '1rem 0',
    textAlign: 'center',
    fontSize: '6rem',
    fontWeight: 500,
    color: theme.palette.primary.main,
    fontFamily: theme.font.family.main
  },
  loginClaim: {
    margin: '1rem 0',
    textAlign: 'center',
    fontSize: '1.2rem',
    fontStyle: 'italic',
    color: '#535353'
  },
  loginCircle: {
    margin: '1rem 0',
    textAlign: 'center'
  },
  btn: {
    fontSize: '20px'
  },
  link: {
    textDecoration: 'none',
    color: '#ffffff'
  },
  img: {
    width: '80%'
  }
});

class AccessMain extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes, t} = this.props;
    return (<section className={classes.login}>
      <div className={classes.loginWrapper}>
        <div className={classes.loginName}>SIGC</div>
        <Typography className={classNames(classes.loginClaim)}>Herramienta centralizada del SIG Corporativo de la Junta de Andalucía</Typography>
        <div className={classes.loginCircle}><img className={classes.img} src={imgMain} alt="main"/></div>
        <Link className={classNames(classes.link)} to="/datasets">
          <Button variant="contained" color="primary" className={classNames(classes.btn)}>{t('access')}</Button>
        </Link>
      </div>
    </section>);
  }
}
AccessMain.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};
export default translate()(withStyles(styles, {withTheme: true})(AccessMain));
