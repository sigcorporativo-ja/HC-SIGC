import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import HCForm from 'components/HCForm';
import SelectSearchOptions from 'components/form/SelectSearchOptions';

const styles = theme => ({root: {}});

class AddDBConnection extends Component {

  render() {
    const {
      validator,
      t,
      classes,
      model,
      onChange,
      onFocus
    } = this.props;
    let {allConections} = this.props;
    if (allConections === undefined) {
      allConections = model.dbconnections;
    }

    return (<HCForm onChange={onChange} onFocus={onFocus} model={model} inheritedClasses={{
        root: classes.root
      }} validator={validator}>
      <SelectSearchOptions name='dbconnections' options={allConections} title={false} textLabel={t('connections_select')} isMulti={true} searchOpenMenu={true}/>
    </HCForm>);
  }
}

AddDBConnection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddDBConnection));
