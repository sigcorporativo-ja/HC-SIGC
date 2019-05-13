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

class AddDBConnection extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: []
    };
  }

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
      <FormInput type="text" name="username" label={t('name')} required={true} margin="normal" fullWidth={true} autoFocus={true}/>
      <FormInput type="password" name="password" label={t('password')} required={true} margin="normal" fullWidth={true}/>
    </HCForm>);
  }
}

AddDBConnection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddDBConnection));
