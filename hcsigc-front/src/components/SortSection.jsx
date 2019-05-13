/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';

const styles = theme => ({
  root: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end'
  }
});

class SortSection extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes} = this.props;
    return (<div className={classes.root}>
      {this.props.children}
    </div>);
  }
}

SortSection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, {withTheme: true})(SortSection);
