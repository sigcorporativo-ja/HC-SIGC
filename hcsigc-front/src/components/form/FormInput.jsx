import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import TextField from '@material-ui/core/TextField';
import FormHelperText from '@material-ui/core/FormHelperText';

const styles = theme => ({});

class FormInput extends Component {

  handlerOnchange = (e) => {
    const {onChange} = this.props;
    if (typeof onChange === 'function') {
      onChange(e);
    }
  };

  handlerOnBlur = (e) => {
    const {onBlur} = this.props;
    if (typeof onBlur === 'function') {
      onBlur(e);
    }
  };

  render() {
    const {
      t,
      tReady,
      required,
      value,
      validator,
      changeFormCb,
      name,
      ...other
    } = this.props;
    let helper = null;
    const error = validator && validator[name]
      ? validator[name].isInvalid
      : false;
    const message = validator && validator[name]
      ? validator[name].message
      : '';
    if (error === true) {
      helper = (<FormHelperText error={true}>{t(message || '')}</FormHelperText>);
    }
    return (<div>
      <TextField {...other} name={name} variant='outlined' error={error} required={required} value={value} onChange={this.handlerOnchange} onBlur={this.handlerOnBlur}/> {helper}
    </div>);
  }
}

FormInput.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(FormInput));
