import React from 'react'

import { 
  Form, Icon, Button
} from 'semantic-ui-react'

import {
  GithubPicker
} from 'react-color'

const echelle = [
  { key: '2', text: '2', value: '2' },
  { key: '3', text: '3', value: '3' },
  { key: '4', text: '4', value: '4' },
  { key: '5', text: '5', value: '5' },
  { key: '6', text: '6', value: '6' },
  { key: '7', text: '7', value: '7' },
  { key: '8', text: '8', value: '8' },
  { key: '9', text: '9', value: '9' },
  { key: '10', text: '10', value: '10' }
]

const forme = [
  {
    key: 'Étoile',
    text: 'Étoile',
    value: 'Étoile',
    icon: 'star outline',
  },
  {
    key: 'Smiley',
    text: 'Smiley',
    value: 'Smiley',
    icon: 'smile outline',
  },
  {
    key: 'Coeur',
    text: 'Coeur',
    value: 'Coeur',
    icon: 'heart outline',
  },
  {
    key: 'Pouce',
    text: 'Pouce',
    value: 'Pouce',
    icon: 'thumbs up outline',
  }
];

export default class QuestionPoints extends React.Component {
  state = {
    echelle: 0,
    forme: '',
    isColorPicker: false,
    colorPicker: 'yellow'
  }

  render() {
    return (
      <React.Fragment>
        <Form.Group>
          <Form.Select
            label='Échelle'
            options={echelle}
            placeholder='5'
            onChange={(e, data) => {
              this.setState({ echelle: data.value })
            }}
          />
          <Form.Select
            label='Forme'
            options={forme}
            placeholder='Étoile'
            onChange={(e, data) => {
              this.setState({ forme: data.value })
            }}
          />
          <div>
            <p style={{ fontWeight: "bold" }}>Couleur</p>
            <Button
              circular
              size='big'
              style={{ background: this.state.colorPicker }}
              onClick={() =>
                this.setState({ isColorPicker: true })
              }
            />
            {this.state.isColorPicker?
              <GithubPicker
                color={this.state.colorPicker}
                onChangeComplete={(color) => {
                  this.setState({
                    isColorPicker: false,
                    colorPicker: color.hex
                  })
                }}
              />:''
            }
          </div>
          <div>
            <p style={{ fontWeight: 'bold' }}>Icon</p>
            {(this.state.forme === '')?
              <Icon 
                name='star'
                color='yellow'
                size='big'
              />:
              <Icon 
                name='star' 
                color={this.state.colorPicker}
              />
            }
          </div>
        </Form.Group>
      </React.Fragment>
    );
  }
}