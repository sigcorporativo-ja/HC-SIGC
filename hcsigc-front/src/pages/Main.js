import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import {withStyles} from '@material-ui/core/styles';

import Layout from 'components/Layout.jsx';
import AccessMain from 'components/AccessMain';

const styles = theme => ({});

class Main extends Component {
  render() {
    return (<Layout>
      <AccessMain/>
    </Layout>);
  }
}
Main.propTypes = {
  classes: PropTypes.object.isRequired
};

export default translate()(withStyles(styles)(Main));
