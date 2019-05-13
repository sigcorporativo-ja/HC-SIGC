import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import FormInput from 'components/form/FormInput';

const styles = theme => ({});

const validators = [
  {
    func: (e, props) => {
      let error = false;
      if (!isNaN(e.target.value) && typeof props.max === 'number') {
        error = parseFloat(e.target.value) > props.max;
      }
      return error;
    },
    errorMessage: (props) => 'invalid_max_number'
  }, {
    func: (e, props) => {
      let error = false;
      if (!isNaN(e.target.value) && typeof props.min === 'number') {
        error = parseFloat(e.target.value) < props.min;
      }
      return error;
    },
    errorMessage: (props) => 'invalid_min_number'
  }
];

class NumberInput extends Component {

  validateState() {
    return this.wrappedInput.validateState();
  }

  createRef = (instance) => {
    this.wrappedInput = instance;
  };

  render() {
    const {error, errorMessage, value} = this.props.model;
    const defaultValue = value == null
      ? this.props.value
      : value;
    return (<FormInput innerRef={this.createRef} {...this.props} error={error} errorMessage={errorMessage} value={defaultValue} validators={validators} type='number'/>);
  }
}

NumberInput.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(NumberInput));
