import React, {Component} from 'react';
import {getUserPermissions} from 'controllers/AuthController';

/**
 * This function checks if the user has
 * the required permissions
 */
export const hasPermissions = (permissions) => {
  if (permissions) {
    const userPermissions = getUserPermissions();
    return permissions.every(p => userPermissions.includes(p));
  } else {
    return true;
  }
};

/**
 * This function checks if the user any
 * required permissions
 */
export const hasAnyPermission = (permissions) => {
  if (permissions) {
    const userPermissions = getUserPermissions();
    return permissions.some(p => userPermissions.includes(p));
  } else {
    return true;
  }
};

/**
 * This function wraps a component and renders it
 * if the user has the required permissions
 */
export const secured = (flex) => (WrappedComponent) => class SecurizedComponent extends Component {
  render() {
    if (flex === true) {
      return hasAnyPermission(this.props.permissions)
        ? React.createElement(WrappedComponent, {
          ...this.props
        }, this.props.children)
        : null;
    } else {
      return hasPermissions(this.props.permissions)
        ? React.createElement(WrappedComponent, {
          ...this.props
        }, this.props.children)
        : null;
    }
  }
};