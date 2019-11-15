import React from 'react'

import { 
  Dropdown 
} from 'semantic-ui-react'

export default class ColorPicker extends React.Component {
  render() {
    return (
      <React.Fragment>
        <Dropdown
          labeled
          button
        />
      </React.Fragment>
    );
  }
}