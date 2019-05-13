import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';
import {getAllDBMS} from 'controllers/DBMSController';

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class AddDBConnection extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allDBMS: [],
      passwordVisual: false,
      showPassword: false
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
    const {t, classes, model} = this.props;

    const _model = {
      ...model,
      dbms: model.dbms.name
    };

    return (<HCForm className={classNames(classes)} model={_model} inputProps={{
        readOnly: true
      }}>
      <FormInput type="text" name="dbms" margin="normal" label={t('dbms')} fullWidth={true} required={true}/>
      <FormInput type="text" name="name" margin="normal" label={t('name')} fullWidth={true} required={true}/>
      <FormInput type="text" name="host" required={true} margin="normal" label={t('host')} fullWidth={true}/>
      <FormInput type="number" name="port" required={true} margin="normal" label={t('port')} fullWidth={true}/>
      <FormInput type="text" name="database" required={true} margin="normal" label={t('database')} fullWidth={true}/>
      <FormInput type="text" name="schema" required={true} margin="normal" label={t('schema')} fullWidth={true}/>
      <FormInput type="text" name="user" required={true} margin="normal" label={t('user')} fullWidth={true}/>
    </HCForm>);
  }
}

AddDBConnection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddDBConnection));
