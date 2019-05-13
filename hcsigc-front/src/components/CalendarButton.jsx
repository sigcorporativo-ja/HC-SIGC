/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import {translate} from 'react-i18next';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';

import Tooltip from '@material-ui/core/Tooltip'
import Calendar from '@material-ui/icons/Today';
import IconButton from '@material-ui/core/IconButton';

const styles = theme => ({
  root: {
    marginLeft: '1rem',
    color: '#ffffff'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  },
  icon: {
    color: '#01579B',
    marginTop: '20px'
  }
});

class CalendarButton extends Component {

  handleClick = (e) => {
    const {action} = this.props;
    if (typeof action === 'function') {
      action(e)
    }
  }

  render() {
    const {classes, t} = this.props;
    return (<Tooltip title={t('calendar')} classes={{
        tooltip: classes.lightTooltip
      }}>
      <IconButton onClick={this.handleClick} className={classNames(classes.icon)}>
        <Calendar/>
      </IconButton>
    </Tooltip>);
  }
}

CalendarButton.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(CalendarButton));
