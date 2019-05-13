/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import VisibilityFilter from './VisibilityFilter';

import PersonIcon from '@material-ui/icons/Person';
import GroupIcon from '@material-ui/icons/Group';
import PublicIcon from '@material-ui/icons/Public';

const styles = theme => ({
  root: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    marginLeft: '10%',
    marginTop: '1rem'
  },
  icon: {
    color: '#01579b',
    alignSelf: 'center',
    marginRight: '15px'
  }
});

class VisibilityFilters extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes, t, onCheck, initialChecked} = this.props;
    const checked = (initialChecked.includes('private'))
      ? true
      : false;

    return (<div className={classes.root}>
      <VisibilityFilter onCheck={onCheck} value='private' initialChecked={checked} tooltip={t('filter_private')}>
        <PersonIcon className={classNames(classes.icon)}/>
      </VisibilityFilter>
      <VisibilityFilter onCheck={onCheck} value='groups' tooltip={t('filter_groups')}>
        <GroupIcon className={classNames(classes.icon)}/>
      </VisibilityFilter>
      <VisibilityFilter onCheck={onCheck} value='l_global' tooltip={t('filter_public')}>
        <PublicIcon className={classNames(classes.icon)}/>
      </VisibilityFilter>
    </div>);
  }
}

VisibilityFilters.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(VisibilityFilters));
