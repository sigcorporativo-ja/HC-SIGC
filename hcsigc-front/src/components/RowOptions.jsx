/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';

const styles = theme => ({
  row: {
    display: 'flex',
    flexDirection: 'row',
  }
});

class RowOptions extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes} = this.props;
    return (<div className={classes.row}>
      {this.props.children}
    </div>);
  }
}

RowOptions.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, {withTheme: true})(RowOptions);
