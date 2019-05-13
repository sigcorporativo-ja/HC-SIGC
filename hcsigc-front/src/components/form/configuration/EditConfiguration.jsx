import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class EditConfiguration extends Component {

  render() {
    const {
      validator,
      t,
      classes,
      model,
      onChange,
      onKeyPress
    } = this.props;

    return (<HCForm onChange={onChange} onKeyPress={onKeyPress} model={model} className={classNames(classes.root)} validator={validator}>
      <FormInput type="text" inputProps={{
          readOnly: true
        }} name="key" label={t('key')} required={true} margin="normal" fullWidth={true}/>
      <FormInput type="text" name="value" label={t('value')} required={true} autoFocus={true} margin="normal" fullWidth={true}/>
      <FormInput type="text" multiline={true} name="description" label={t('description')} required={true} margin="normal" fullWidth={true}/>
    </HCForm>);
  }
}

EditConfiguration.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(EditConfiguration));
