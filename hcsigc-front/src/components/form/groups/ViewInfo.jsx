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
      <FormInput type="text" name='org_unit' label={t('org_unit')} margin="normal" fullWidth={true}/>
      <FormInput type="text" name="name" label={t('name')} margin="normal" fullWidth={true}/>
      <FormInput type="text" multiline={true} name="description" label={t('description')} margin="normal" fullWidth={true}/>
    </HCForm>);
  }
}

ViewInfo.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ViewInfo));
