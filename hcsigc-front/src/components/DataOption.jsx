/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import Button from '@material-ui/core/Button';
import FolderShared from '@material-ui/icons/FolderShared';
import Tooltip from '@material-ui/core/Tooltip';
import {Link} from 'react-router-dom';

const styles = theme => ({
  root: {
    display: 'flex'
  },
  icon: {
    marginLeft: '5px'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  },
  link: {
    textDecoration: 'none',
    color: '#ffffff',
    display: 'inherit'
  }
});

class DataOption extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes, t, style} = this.props;
    return (<div className={classes.root}>
      <Tooltip title={t('data')} classes={{
          tooltip: classes.lightTooltip
        }}>
        <Link to="/datasets" className={classNames(classes.link)}>
          <Button className={classNames(style)}>
            {t('data')}
            <FolderShared className={classNames(classes.icon)}/>
          </Button>
        </Link>
      </Tooltip>
    </div>);
  }
}

DataOption.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(DataOption));
