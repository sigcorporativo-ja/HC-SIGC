/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';

import HCBar from 'components/HCBar';
import HCFooter from 'components/HCFooter';

const styles = theme => ({
  html: {
    backgroundColor: '#fff',
    color: '#212121',
    textRendering: 'optimizeLegibility',
    fontSize: '14px',
    margin: 0,
    padding: 0,
    paddingTop: '4rem',
    display: 'flex',
    flexDirection: 'column'
  },
  content: {
    display: 'flex',
    flexGrow: 1,
    flexDirection: 'column',
    height: 'calc(100vh - 155px)'
  }
});

class Layout extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  reload = () => {
    this.forceUpdate();
  }

  render() {
    const {classes} = this.props;
    return (<div className={classes.html}>
      <HCBar forceUpdate={this.reload}/>
      <div className={classes.content}>
        {this.props.children}
      </div>
      <div>
        <HCFooter/>
      </div>
    </div>);
  }
}

Layout.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, {withTheme: true})(Layout);
