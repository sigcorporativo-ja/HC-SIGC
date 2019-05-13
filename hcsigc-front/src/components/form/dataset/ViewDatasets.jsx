import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';
import {getUserSession} from 'controllers/AuthController';

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class ViewDatasets extends Component {

  isGlobal = () => {
    const {t, model} = this.props;
    if (model.global) {
      return t('yes')
    } else {
      return 'No'
    }
  }

  render() {
    const {t, classes, model} = this.props;

    return (<HCForm model={model} className={classNames(classes.root)} inputProps={{
        readOnly: true
      }}>
      <FormInput type="text" name="name" label={t('name')} required={true} margin="normal" fullWidth={true} autoFocus={true}/>
      <FormInput type="text" name="description" label={t('description')} required={true} margin="normal" fullWidth={true}/>
      <FormInput type="text" multiline={true} name="tableName" label={t('table_name')} required={true} margin="normal" fullWidth={true}/>
      <Typography>
        {
          getUserSession()
            ? <React.Fragment>
                {t('select_global')}
                {this.isGlobal()}
              </React.Fragment>
            : null
        }
      </Typography>
    </HCForm>);
  }
}

ViewDatasets.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ViewDatasets));
