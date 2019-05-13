/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

const styles = theme => ({
  root: {
    display: 'flex',
    flexDirection: 'row'
  },
  lightTooltip: {
    background: theme.palette.common.white,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[3],
    fontSize: 15
  }

});

class Navigator extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  onInput = (e) => {
    if (e.keyCode === 13) {
      this.props.onFilter(e.target.value);
      e.target.value = '';
    }
  }

  render() {
    const {classes} = this.props;
    return (<div className={classes.root}>
      {this.props.children}
    </div>);
  }

  showInput(e) {
    console.log(e.target.value);
    console.log(this);
  }
}

Navigator.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(Navigator));
