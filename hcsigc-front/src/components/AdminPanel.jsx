import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';

import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import Tooltip from '@material-ui/core/Tooltip';

import MenuIcon from '@material-ui/icons/Menu';

import AsidePanel from 'components/AsidePanel';

import {secured} from 'utils/Securization';

const styles = theme => ({
  list: {
    width: 250
  },
  menuIcon: {
    color: '#fff'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  }
});

class AdminPanel extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false
    };
  }

  toggleDrawer = () => () => {
    this.setState({
      open: !this.state.open
    });
  };

  render() {
    const {classes, t} = this.props;

    return (<div className={classes.root}>
      <Tooltip title={t('admin_panel')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <Button onClick={this.toggleDrawer()}>
          <MenuIcon className={classNames(classes.menuIcon)}/>
        </Button>
      </Tooltip>
      <Drawer color={'primary'} open={this.state.open} onClose={this.toggleDrawer('left', false)} disableRestoreFocus={true}>
        <div tabIndex={0} role="button" onClick={this.toggleDrawer()} onKeyDown={this.toggleDrawer()}>
          <div className={classes.list}>
            <AsidePanel/>
          </div>
        </div>
      </Drawer>
    </div>);
  }
}

AdminPanel.propTypes = {
  classes: PropTypes.object.isRequired
};

export default secured(true)(translate()(withStyles(styles)(AdminPanel)));
