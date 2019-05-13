import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import FormInput from 'components/form/FormInput';

const styles = theme => ({});

const validator = {
  func: (e, props) => {
    const regexp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    return !(regexp.test(e.target.value));
  },
  errorMessage: (props) => 'invalid_email'
}

class EmailInput extends Component {

  validateState() {
    return this.wrappedInput.validateState();
  }

  createRef = (instance) => {
    this.wrappedInput = instance;
  };

  render() {
    const {error, errorMessage, value} = this.props.model;
    return (<FormInput innerRef={this.createRef} error={error} errorMessage={errorMessage} value={value} validators={[validator]} type='text' {...this.props}/>);
  }
}

EmailInput.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(EmailInput));
