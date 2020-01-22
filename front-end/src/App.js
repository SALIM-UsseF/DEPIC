import React from 'react'

import {
  Client,
  MainView 
} from './lib'
import { url } from './lib/Helpers/Settings'

const client = new Client(url)

export default class App extends React.Component {
  render() {
    return (
      <div>
        <MainView
          client={client}
          lang='fr'
        />
      </div>
    );
  }
}