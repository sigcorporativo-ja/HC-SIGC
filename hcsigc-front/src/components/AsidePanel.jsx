import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import {withRouter} from 'react-router-dom';
import classNames from 'classnames';

import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';

import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import Typography from '@material-ui/core/Typography';

import {Link} from 'react-router-dom';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import AccountBalance from '@material-ui/icons/AccountBalance';
import GroupIcon from '@material-ui/icons/Group';
import MemoryIcon from '@material-ui/icons/Memory';
import AllInboxIcon from '@material-ui/icons/Inbox';
import DeviceHub from '@material-ui/icons/DeviceHub';

import PersonIcon from '@material-ui/icons/Person';

import SecurizedComponent from 'components/SecurizedComponent';

const primaryBlue = '#1C74A6';

const styles = theme => ({
  root: {
    // backgroundColor: '#1C74A6',
    paddingTop: 0,
    display: 'flex',
    flexDirection: 'column'
  },
  button: {
    color: primaryBlue,
    width: '100%',
    display: 'flex',
    justifyContent: 'flex-start'
  },
  currentButton: {
    color: primaryBlue,
    width: '100%',
    display: 'flex',
    justifyContent: 'flex-start',
    backgroundColor: '#E0E0E0',
    borderRadius: '0px'
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center'
  },
  link: {
    textDecoration: 'none'
  },
  divider: {
    backgroundColor: '#d2d2d2'
  },
  title: {
    color: '#333',
    fontSize: '1.2rem',
    display: 'flex',
    flexGrow: 1,
    marginLeft: '1.2rem'
  },
  iconPanelContainer: {
    marginRight: '0.3rem'
  },
  iconPanel: {
    color: '#b1b1b1'
  },
  textPanel: {
    color: '#333',
    textTransform: 'capitalize',
    fontWeight: 'normal',
    fontSize: '0.9rem'
  },
  selected: {
    backgroundColor: '#9E9E9E'
  }
});

const mapItem = (item, props) => {
  const {classes, t} = props;
  return (<SecurizedComponent key={item.key} permissions={item.permission}>
    <Link className={classNames(classes.link)} to={item.to}>
      <Button className={item.to === props.history.location.pathname
          ? classNames(classes.currentButton)
          : classNames(classes.button)}>
        <div className={classes.iconPanelContainer}>
          {
            React.cloneElement(item.icon, {
              ...item.icon.props,
              className: classNames(classes.iconPanel)
            })
          }
        </div>
        <Typography className={classNames(classes.textPanel)}>
          {t(item.i18n)}
        </Typography>
      </Button>
    </Link>
  </SecurizedComponent>);
};

const mapItems = (items, props) => {
  return items.map(item => mapItem(item, props))
};

const topItemsPanel = [
  {
    key: '/ou',
    to: '/ou',
    i18n: 'org_units',
    icon: <AccountBalance/>,
    permission: ['ADMIN_UO']
  }, {
    key: '/users',
    to: '/users',
    i18n: 'users',
    icon: <PersonIcon/>,
    permission: ['ADMIN_USERS']
  }, {
    key: '/groups',
    to: '/groups',
    i18n: 'groups',
    icon: <GroupIcon/>,
    permission: ['ADMIN_GROUP_USERS', 'ADMIN_GROUP']
  }, {
    key: '/configuration',
    to: '/configuration',
    i18n: 'configuration',
    icon: <MemoryIcon/>,
    permission: ['ADMIN_CONFIG']
  }, {
    key: '/dbms',
    to: '/dbms',
    i18n: 'dbms',
    icon: <AllInboxIcon/>,
    permission: ['ADMIN_DATABASES']
  }, {
    key: '/dbconnection',
    to: '/dbconnection',
    i18n: 'dbconnection',
    icon: <DeviceHub/>,
    permission: ['ADMIN_DATABASES']
  }
];

class AsidePanel extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      color: 'white'
    };
  }

  toggleDrawer = () => () => {
    this.setState({
      open: !this.state.open
    });
  };

  render() {
    const {classes, t} = this.props;
    const topMappedItems = mapItems(topItemsPanel, this.props);
    return (<List className={classNames(classes.root)}>
      <div className={classes.header}>
        <Typography variant='title' className={classNames(classes.title)}>
          {t('admin_panel')}
        </Typography>
        <IconButton>
          <KeyboardArrowLeft className={classNames(classes.iconPanel)}/>
        </IconButton>
      </div>
      <Divider className={classNames(classes.divider)}/> {topMappedItems}
      <Divider className={classNames(classes.divider)}/>
    </List>);
  }
}

AsidePanel.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withRouter(withStyles(styles, {withTheme: true})(AsidePanel)));
