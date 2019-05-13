/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';

import Checkbox from '@material-ui/core/Checkbox';

const styles = theme => ({
  root: {
    display: 'flex'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  }
});

class FilterVisibility extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  onChange = (e) => {
    const {onCheck} = this.props;
    onCheck(e);
  }

  render() {
    const {classes, tooltip, value, initialChecked} = this.props;
    return (<div className={classes.root}>
      <Tooltip title={tooltip} classes={{
          tooltip: classes.lightTooltip
        }}>
        <Checkbox value={value} checked={initialChecked} onChange={this.onChange} color="primary"></Checkbox>
      </Tooltip>
      {this.props.children}
    </div>);
  }
}

FilterVisibility.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, {withTheme: true})(FilterVisibility);
