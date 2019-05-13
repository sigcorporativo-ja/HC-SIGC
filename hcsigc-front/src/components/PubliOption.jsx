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
import ViewList from '@material-ui/icons/ViewList';
import Tooltip from '@material-ui/core/Tooltip';

const styles = theme => ({
  root: {
    display: 'flex'
  },
  option: {
    color: '#ffffff',
    textTransform: 'capitalize',
    marginTop: '5px'
  },
  icon: {
    marginLeft: '5px'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  }

});

class PubliOption extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes, t, style} = this.props;
    return (<div className={classes.root}>
      <Tooltip title={t('publication')} classes={{
          tooltip: classes.lightTooltip
        }} placement="bottom">
        <Button className={classNames(style)}>
          {t('publication')}
          <ViewList className={classNames(classes.icon)}/>
        </Button>
      </Tooltip>
    </div>);
  }
}

PubliOption.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(PubliOption));
