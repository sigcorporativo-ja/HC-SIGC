/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import {secured} from 'utils/Securization';

class SecurizedComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (<React.Fragment>{this.props.children}</React.Fragment>);
  }
}

export default secured(true)((SecurizedComponent));
