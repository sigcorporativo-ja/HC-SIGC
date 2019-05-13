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

class OrderDateDatasets extends Component {

  render() {
    const {t, classes, model, onChange, validator} = this.props;

    return (<HCForm model={model} onChange={onChange} validator={validator} className={classNames(classes.root)}>
      <FormInput InputLabelProps={{
          shrink: true
        }} type="date" name="startDate" label={t('from')} required={true} margin="normal" fullWidth={true} autoFocus={true}/>
      <FormInput InputLabelProps={{
          shrink: true
        }} type="date" label={t('to')} name="endDate" required={true} margin="normal" fullWidth={true}/>
    </HCForm>);
  }
}

OrderDateDatasets.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(OrderDateDatasets));