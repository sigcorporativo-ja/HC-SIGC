import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';
import SecurizedComponent from 'components/SecurizedComponent';
import Checkbox from '@material-ui/core/Checkbox';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class AddDatasets extends Component {

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
      <FormInput type="text" name="name" label={t('name')} required={true} margin="normal" fullWidth={true} autoFocus={true}/>
      <FormInput type="text" name="description" label={t('description')} required={true} margin="normal" fullWidth={true}/>
      <SecurizedComponent permissions={['ADMIN_UO']}>
        <Typography>{t('select_global')}
          <Checkbox onChange={onChange} name="global" checked={model.global} color="primary"/>
        </Typography>
      </SecurizedComponent>
      <FormInput id="datasetFile" name="associatedFile" inputProps={{
          accept: '.csv, .shp'
        }} type="file"/>
      <Typography>{t('upload_message')}</Typography>
    </HCForm>);
  }
}

AddDatasets.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddDatasets));
