/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';

import IconButton from '@material-ui/core/IconButton';
import Tooltip from '@material-ui/core/Tooltip';

const styles = theme => ({
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  }
});

class ViewButton extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  handleClick = (evt) => {
    const {name} = this.props;
    this.props.action(this.props.id, evt, name);
  };

  render() {
    const {classes, tooltip, placeTooltip} = this.props;
    return (<div>
      <Tooltip title={tooltip} classes={{
          tooltip: classes.lightTooltip
        }} placement={placeTooltip}>
        <IconButton onClick={this.handleClick} color='inherit'>
          {this.props.children}
        </IconButton>
      </Tooltip>
    </div>);
  }
}
ViewButton.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};
export default withStyles(styles, {withTheme: true})(ViewButton);
