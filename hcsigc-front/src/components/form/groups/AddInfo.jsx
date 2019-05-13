import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import FormInput from 'components/form/FormInput';
import {hasPermissions} from 'utils/Securization';
import SelectSearchOptions from 'components/form/SelectSearchOptions';

import {getAllOrganizationUnits, getMyOrganizationUnit} from 'controllers/OrganizationUnitController';

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class AddInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allUO: []
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
    const {
      validator,
      t,
      classes,
      model,
      onChange,
      onFocus
    } = this.props;

    return (<HCForm onChange={onChange} onFocus={onFocus} model={model} className={classNames(classes.root)} validator={validator}>
      {this.showSelect()}
      <FormInput type="text" name="name" label={t('name')} required={true} margin="normal" fullWidth={true} autoFocus={true}/>
      <FormInput type="text" multiline={true} name="description" label={t('description')} required={false} margin="normal" fullWidth={true}/>
    </HCForm>);
  }
}

AddInfo.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddInfo));
