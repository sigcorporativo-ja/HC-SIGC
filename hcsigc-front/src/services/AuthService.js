import decode from 'jwt-decode';
import axios from 'axios';
import { postRequest } from './ApiService';

const CONTEXT_LOGIN = '/auth/login';
const CONTEXT_LOGOUT = '/auth/logout';

export const login = (data) => {
  const headers = {};
  if (isLoggedIn()) {
    headers['Authorization'] = `Bearer ${getToken_()}`;
  }

  return postRequest(CONTEXT_LOGIN, data, headers).then(res => {
    setToken_(res.data.authToken); // Setting the token in localStorage
    axios.defaults.headers.common['Authorization'] = `Bearer ${res.data.authToken}`;
    return Promise.resolve(res);
  });
};

export const logout = () => {
  const headers = {};
  if (isLoggedIn()) {
    headers['Authorization'] = `Bearer ${getToken_()}`;
  }

  return postRequest(CONTEXT_LOGOUT, undefined, headers).then(res => {
    localStorage.removeItem('jwtToken');
    return Promise.resolve(res);
  });
};

export const isLoggedIn = () => {
  // Checks if there is a saved token and it's still valid
  const token = getToken_(); // GEtting token from localstorage
  return !!token && !isTokenExpired_(token); // handwaiving here
};

// Using jwt-decode npm package to decode the token
export const getProfile = () => isLoggedIn() ?
  decode(getToken_()) :
  null;

// Retrieves the user token from localStorage
const getToken_ = () => localStorage.getItem('jwtToken');

// Saves user token to localStorage
const setToken_ = (idToken) => localStorage.setItem('jwtToken', idToken);

const isTokenExpired_ = (token) => {
  try {
    const decoded = decode(token);
    if (decoded.exp < Date.now() / 1000) { // Checking if token is expired. N
      return false; //TODO Arreglar timeout en servidor
    } else {
      return false;
    }
  } catch (err) {
    return false;
  }
};
