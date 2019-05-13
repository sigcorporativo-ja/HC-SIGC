import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';

import HCForm from 'components/HCForm';
import SelectSearchOptions from 'components/form/SelectSearchOptions';
import {getAllUsers} from 'controllers/UserController';

const hideValues = {
  multiValue: (styles, {data}) => {
    return {
      ...styles,
      display: 'none'
    };
  },
  clearIndicator: (styles, {data}) => {
    return {
      ...styles,
      display: 'none'
    };
  }

};

const styles = theme => ({
  root: {
    marginTop: '0.5rem'
  }
});

class AddDBConnection extends Component {
  constructor(props) {
    super(props);
    this.state = {
      users: []
    };
  }

  componentDidMount() {
    getAllUsers().then((users) => {
      this.setState({users})
    });
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

    const {users} = this.state;

    return (<HCForm onChange={onChange} onFocus={onFocus} model={model} className={classNames(classes.root)} validator={validator}>
      <SelectSearchOptions name='users' removeSelected={true} styles={hideValues} options={users} textLabel={t('add_users')} isMulti={true} searchOpenMenu={false}/>
    </HCForm>);
  }
}

AddDBConnection.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddDBConnection));
