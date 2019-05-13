import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';
import {getAllDBMS} from 'controllers/DBMSController';
import SelectSearchOptions from 'components/form/SelectSearchOptions';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class EditDBConnection extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allDBMS: [],
      passwordVisual: false,
      showPassword: false,
      helper: '',
      error: false
    };
  }

  componentDidMount() {
    getAllDBMS().then((allDBMS) => {
      this.setState({allDBMS})
    });
  }

  handleClickShowPassword = () => {
    this.setState(state => ({
      showPassword: !state.showPassword
    }));
  };

  render() {
    const {t, validator, classes, model, onChange} = this.props;
    const {allDBMS, showPassword} = this.state;

    let seePassword = (<InputAdornment position="end">
      <IconButton aria-label="Toggle password visibility" onClick={this.handleClickShowPassword}>
        {
          this.state.showPassword
            ? <Visibility/>
            : <VisibilityOff/>
        }
      </IconButton>
    </InputAdornment>);

    return (<HCForm onChange={onChange} model={model} validator={validator} className={classNames(classes)}>
      <SelectSearchOptions name='dbms' options={allDBMS} textLabel={t('dbms')} searchOpenMenu={false}/>
      <FormInput type="text" name="name" margin="normal" label={t('name')} fullWidth={true} required={true} autoFocus={true}/>
      <FormInput type="text" name="host" required={true} margin="normal" label={t('host')} fullWidth={true}/>
      <FormInput type="number" name="port" required={true} margin="normal" label={t('port')} fullWidth={true}/>
      <FormInput type="text" name="database" required={true} margin="normal" label={t('database')} fullWidth={true}/>
      <FormInput type="text" name="schema" required={true} margin="normal" label={t('schema')} fullWidth={true}/>
      <FormInput type="text" name="user" required={true} margin="normal" label={t('user')} fullWidth={true}/>
      <FormInput type={showPassword
          ? 'text'
          : 'password'} InputProps={{
          endAdornment: seePassword
        }} name="password" required={true} onKeyPress={this.handleOnKeyPass} margin="normal" label={t('password')} fullWidth={true}/>
      <FormInput type="password" name="password_confirm" onChange={this.handlerOnConfirmPass} required={true} margin="normal" label={t('password_confirm')} fullWidth={true}/>
    </HCForm>);
  }
}

EditDBConnection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(EditDBConnection));
