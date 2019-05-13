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

class ViewInfo extends Component {

  render() {
    const {t, classes, model} = this.props;
    const _model = {
      ...model,
      org_unit: model.org_unit.name
    };
    return (<HCForm model={_model} className={classNames(classes.root)} inputProps={{
        readOnly: true
      }}>
      <FormInput type="text" name="org_unit" margin="normal" label={t('org_unit')} fullWidth={true} autoFocus={true}/>
      <FormInput type="text" name="name" margin="normal" label={t('name')} fullWidth={true} autoFocus={true}/>
      <FormInput type="text" name="surname" margin="normal" label={t('surname')} fullWidth={true}/>
      <FormInput type="email" name="email" margin="normal" label={t('mail')} fullWidth={true}/>
      <FormInput type="text" name="username" margin="normal" label={t('username')} fullWidth={true}/>
      <FormInput type="number" name="quota" margin="normal" label={t('disk_share')} fullWidth={true}/>
      <FormInput type="number" name="usedQuota" margin="normal" label={t('disk_used')} fullWidth={true}/>
    </HCForm>);
  }
}

ViewInfo.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ViewInfo));
