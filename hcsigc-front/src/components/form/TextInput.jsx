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
      if (typeof e.target.value === 'string' && typeof props.maxLenght === 'number') {
        error = e.target.value.length > props.maxLenght;
      }
      return error;
    },
    errorMessage: (props) => 'invalid_max_length_text'
  }, {
    func: (e, props) => {
      let error = false;
      if (typeof e.target.value === 'string' && typeof props.minLenght === 'number') {
        error = e.target.value.length < props.minLenght;
      }
      return error;
    },
    errorMessage: (props) => 'invalid_min_length_text'
  }
];

class TextInput extends Component {

  validateState() {
    return this.wrappedInput.validateState();
  }

  createRef = (instance) => {
    this.wrappedInput = instance;
  };

  render() {
    const {error, errorMessage, value} = this.props.model;
    return (<FormInput innerRef={this.createRef} error={error} errorMessage={errorMessage} value={value} validators={validators} type='text' {...this.props}/>);
  }
}

TextInput.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(TextInput));
