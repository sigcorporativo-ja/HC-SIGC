import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import Item from '@material-ui/core/ListItem';
import ItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import Checkbox from '@material-ui/core/Checkbox';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';

const styles = theme => ({
  root: {
    marginTop: '1rem'
  },
  select: {
    width: '100%'
  },
  item: {
    marginTop: '-10px'
  }
});

const mapPermissions = (permissions, model, disabled, onChange, classes) => permissions.map(data => {
  const checked = model.permissions.map(d => d.id).includes(data.id);
  return (<Item className={classNames(classes.item)} key={data.id}>
    <Checkbox onChange={onChange && onChange(data)} disableRipple={true} tabIndex={-1} color={'primary'} checked={checked} disabled={disabled}/>
    <ItemText primary={`${data.name}`}/>
  </Item>);
});

const mapRoles = (roles) => roles.map(data => {
  return (<MenuItem key={data.id} value={data.id}>{data.name}</MenuItem>);
});

class AddPermissions extends Component {

  render() {
    const {
      classes,
      model,
      selectedRole,
      roles,
      permissions,
      disabled,
      onChange
    } = this.props;
    const _permissions = mapPermissions(permissions, model, disabled, onChange, classes);
    const _roles = mapRoles(roles);

    return (<form className={classNames(classes.root)}>
      {
        disabled === true
          ? null
          : <React.Fragment>
              <InputLabel htmlFor="select-role">Rol</InputLabel>
              <Select onChange={onChange(null)} value={selectedRole} inputProps={{
                  name: 'select-role',
                  id: 'select-role'
                }} className={classNames(classes.select)}>
                {_roles}
              </Select>
            </React.Fragment>
      }
      {_permissions}
    </form>);
  }
}

AddPermissions.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(AddPermissions));
