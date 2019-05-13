/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import {withRouter} from 'react-router-dom';

import UtilOption from './UtilOption';
import MapOption from './MapOption';
import PubliOption from './PubliOption';
import DataOption from './DataOption';

const styles = theme => ({
  root: {
    display: 'flex',
    marginRight: '2rem'
  },
  currentData: {
    color: '#ffffff',
    textTransform: 'capitalize',
    marginTop: '5px',
    borderRadius: '0px',
    borderBottom: "5px solid white",
    marginBottom: '-5.5px'
  },
  data: {
    color: '#ffffff',
    textTransform: 'capitalize',
    marginTop: '5px',
    borderRadius: '0px'
  }
});

class MenuOptions extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes} = this.props;
    const {history} = this.props;
    return (<div className={classes.root}>
      <UtilOption style={history.location.pathname === "/utils"
          ? classes.currentData
          : classes.data} permissions={['ADMIN_UO', 'SHARE_DATA']}/>
      <MapOption style={history.location.pathname === "/maps"
          ? classes.currentData
          : classes.data}/>
      <PubliOption style={history.location.pathname === "/publications"
          ? classes.currentData
          : classes.data}/>
      <DataOption style={history.location.pathname === "/datasets"
          ? classes.currentData
          : classes.data}/>
    </div>);
  }
}

MenuOptions.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withRouter(withStyles(styles, {withTheme: true})(MenuOptions)));
