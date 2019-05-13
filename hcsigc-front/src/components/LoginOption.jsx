/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import Tooltip from '@material-ui/core/Tooltip';
import MenuItem from '@material-ui/core/MenuItem';
import classNames from 'classnames';

import AccountCircle from '@material-ui/icons/AccountCircle';
import IconButton from '@material-ui/core/IconButton';
import Popper from '@material-ui/core/Popper';
import Grow from '@material-ui/core/Grow';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';

import {getUserSession, isLoggedIn} from 'controllers/AuthController';

const styles = theme => ({
  root: {
    display: 'flex'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  },
  textMenu: {
    fontSize: '0.9em'
  },
  icon: {
    fontSize: "1.5vh",
    marginRight: "2rem",
    marginTop: "0.55rem"
  }
});

class LoginOption extends Component {
  constructor(props) {
    super(props);
    this.state = {
      anchorEl: null,
      open: false
    };
  }

  handleClick = event => {
    this.setState(state => ({
      open: !state.open
    }));
  };

  handleClose = event => {
    if (this.anchorEl.contains(event.target)) {
      return;
    }
    this.setState({open: false});
  };

  handleCloseSession = event => {
    this.setState({open: false});
    this.props.onLogout(event);
  };

  render() {
    const {anchorEl, open} = this.state;
    const {classes, t, openDialog} = this.props;
    const user = getUserSession();
    let tooltipUser = 'login'
    if (user) {
      tooltipUser = user.username;
    }

    return (<div className={classes.root}>
      <Tooltip title={t(tooltipUser)} classes={{
          tooltip: classes.lightTooltip
        }}>
        <IconButton color='inherit' aria-owns={anchorEl
            ? 'simple-menu'
            : null} aria-haspopup="true" onClick={this.handleClick} buttonRef={node => {
            this.anchorEl = node;
          }} className={classNames(classes.icon)}>
          <AccountCircle/> {
            user
              ? user.username
              : null
          }
        </IconButton>
      </Tooltip>
      <Popper open={open} anchorEl={this.anchorEl} transition={true}>
        {
          ({TransitionProps, placement}) => (<Grow {...TransitionProps} id="menu-list-grow" style={{
              transformOrigin: placement === 'bottom'
                ? 'center top'
                : 'center bottom'
            }}>
            <Paper className={classNames(classes.paper)}>
              <ClickAwayListener onClickAway={this.handleClose}>
                <MenuList>
                  {
                    isLoggedIn()
                      ? (<div>
                        <MenuItem className={classNames(classes.textMenu)}>{t('my_profile')}</MenuItem>
                        <MenuItem onClick={this.handleCloseSession} className={classNames(classes.textMenu)}>
                          {t('close_session')}
                        </MenuItem>
                      </div>)
                      : (<div>
                        <MenuItem onClick={openDialog} className={classNames(classes.textMenu)}>{t('login')}</MenuItem>
                        <MenuItem onClick={this.handleClose} className={classNames(classes.textMenu)}>{t('create_account')}</MenuItem>
                      </div>)
                  }
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>)
        }
      </Popper>
    </div>);
  }
}

LoginOption.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(LoginOption));
