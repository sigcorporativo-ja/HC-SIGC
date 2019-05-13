import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';
import SelectSearchOptions from 'components/form/SelectSearchOptions';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import {hasPermissions} from 'utils/Securization';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

import {getAllOrganizationUnits, getMyOrganizationUnit} from 'controllers/OrganizationUnitController';

const styles = theme => ({
  root: {
    paddingTop: '0.8rem'
  }
});

class AddInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allUO: [],
      passwordVisual: false,
      showPassword: false,
      helper: '',
      error: false
    };
  }

  componentDidMount() {
    if (hasPermissions(['ADMIN_UO'])) {
      getAllOrganizationUnits().then((allUO) => {
        this.setState({allUO})
      });
    } else {
      getMyOrganizationUnit().then((allUO) => {
        const listOU = [allUO];
        this.setState({
          ...this.state,
          allUO: listOU
        })
      });
    }
  }

  handleClickShowPassword = () => {
    this.setState(state => ({
      showPassword: !state.showPassword
    }));
  };

  showSelect = () => {
    const {t} = this.props;
    const {allUO} = this.state;
    let show = true;
    if (hasPermissions(['ADMIN_UO'])) {
      show = false;
    }
    return <SelectSearchOptions name='org_unit' options={allUO} textLabel={t('org_units')} isDisabled={show} searchOpenMenu={false}/>
  }

  render() {
    const {t, validator, classes, model, onChange} = this.props;
    const {showPassword} = this.state;

    let seePassword = (<InputAdornment position="end">
      <IconButton aria-label="Toggle password visibility" onClick={this.handleClickShowPassword}>
        {
          this.state.showPassword
            ? <Visibility/>
            : <VisibilityOff/>
        }
      </IconButton>
    </InputAdornment>);

    return (<HCForm onChange={onChange} model={model} validator={validator} inheritedClasses={{
        root: classes.root
      }}>
      {this.showSelect()}
      <FormInput type="text" name="name" margin="normal" label={t('name')} fullWidth={true} required={true} autoFocus={true}/>
      <FormInput type="text" name="surname" required={true} margin="normal" label={t('surname')} fullWidth={true}/>
      <FormInput type="email" name="email" required={true} margin="normal" label={t('mail')} fullWidth={true}/>
      <FormInput type="number" name="quota" required={true} margin="normal" label={t('disk_share')} fullWidth={true}/>
      <FormInput type="text" name="username" required={true} margin="normal" label={t('username')} fullWidth={true}/>
      <FormInput type={showPassword
          ? 'text'
          : 'password'} InputProps={{
          endAdornment: seePassword
        }} name="password" required={true} onKeyPress={this.handleOnKeyPass} margin="normal" label={t('password')} fullWidth={true}/>
      <FormInput name="password_confirm" type="password" required={true} margin="normal" label={t('password_confirm')} fullWidth={true}/>
    </HCForm>);
  }
}

AddInfo.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddInfo));
