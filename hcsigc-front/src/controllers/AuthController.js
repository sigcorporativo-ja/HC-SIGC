import { login as loginUser, isLoggedIn as isLoggedInService, getProfile, logout as logoutUser } from 'services/AuthService';

export const getUserPermissions = () => {
  let permissionCodes = [];

  const user = getUserSession();
  if (user && user.permissions) {
    permissionCodes = user.permissions.map(p => p.code);
  }

  return permissionCodes;
};

export const getUserSession = getProfile;

export const login = (data) => {
  return loginUser(data);
};

export const logout = logoutUser;

export const isLoggedIn = isLoggedInService;
