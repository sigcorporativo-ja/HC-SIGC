import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import HCForm from 'components/HCForm';
import SelectSearchOptions from 'components/form/SelectSearchOptions';

const styles = theme => ({
  root: {
    marginTop: '0.5rem',
    width: '28rem'
  }
});

class AddGroupsToShare extends Component {

  render() {
    const {
      validator,
      t,
      classes,
      model,
      onChange,
      onFocus
    } = this.props;
    let {groupsToShare} = this.props;
    if (groupsToShare === undefined) {
      groupsToShare = model.groups;
    }

    return (<HCForm onChange={onChange} onFocus={onFocus} model={model} className={classNames(classes.root)} validator={validator}>
      <SelectSearchOptions name='groups' options={groupsToShare} textLabel={t('groups_share')} isMulti={true} searchOpenMenu={true}/>
    </HCForm>);
  }
}

AddGroupsToShare.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddGroupsToShare));
