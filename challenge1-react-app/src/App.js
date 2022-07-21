import React, { Component } from 'react';
import './App.css';
import {Container, Table} from "reactstrap";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {users: [], filterByAge: '', filterByLastName: '', sortBy: 'name', direction: '', start: '0', limit: '100'};
  }

  componentDidMount() {
    fetch('/users?sortBy=name')
    .then(response => response.json())
    .then(data => this.setState({users: data}));
  }

  searchFilter = () => {
    let queryParams = 'sortBy='+this.state.direction+this.state.sortBy;
    queryParams += '&start='+this.state.start+ '&limit='+this.state.limit;
    if (this.state.filterByLastName !== '') {
      queryParams += '&filter=lastname:'+this.state.filterByLastName;
    }

    if (this.state.filterByLastName !== '' && this.state.filterByAge !== '') {
      queryParams += ',age:'+this.state.filterByAge;
    } else if (this.state.filterByAge !== '') {
      queryParams += '&filter=age:'+this.state.filterByAge;
    }

    fetch('/users?'+queryParams)
    .then(response => response.json())
    .then(data => this.setState({users: data}));
  };

  updateNameFilter = (text) => {
    this.setState({
      ...this.state,
      filterByLastName: text})
  };

  updateAgeFilter = (text) => {
    this.setState({
      ...this.state,
      filterByAge: text})
  };

  updateDirection = (text) => {
    console.log(text);
    if (text === 'asc') {
      this.setState({
        ...this.state,
        direction: ''});
    } else {
      this.setState({
        ...this.state,
        direction: '-'})
    }
  }

  updateSortBy = (text) => {
    if (text === 'Name') {
      this.setState({
        ...this.state,
        sortBy: 'name'});
    } else {
      this.setState({
        ...this.state,
        sortBy: 'age'
      })
    }
  }

  updateStart = (text) => {
    this.setState({
      ...this.state,
      start: text});
  }

  updateLimit = (text) => {
    this.setState({
      ...this.state,
      limit: text});
  }

  render() {
    const {users, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const userList = users.map(user => {
      return <tr key={user.name}>
        <td>{user.id}</td>
        <td style={{whiteSpace: 'nowrap'}}>{user.name}</td>
        <td>{user.age}</td>
        <td>
          {user.address1}
        </td>
        <td>
          {user.address2}
        </td>
      </tr>
    });

    return (
        <div>
          <Container fluid>
            <h3 className={'mt-3 mb-3'}>List of users</h3>
            <div>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">Filter By Last Name</span>
                </div>
                <input type="text" onChange={(event) => this.updateNameFilter(event.target.value)} className="form-control input-sm" aria-label="Enter last name to be filtered by" />
              </div>

              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">Filter By Age</span>
                </div>
                <input type="text" onChange={(event) => this.updateAgeFilter(event.target.value)} className="form-control input-sm" aria-label="Enter age to be filtered by" />
              </div>
            </div>

            <div className="btn-group btn-group-toggle" data-toggle="buttons">
              <span className="input-group-text">Sort By</span>

              <label className="btn btn-secondary active">
                <input type="radio" name="options" id="name"
                       autoComplete="off" checked onChange={(event)  => this.updateSortBy(event.target.id)} /> Name</label>
              <label className="btn btn-secondary">
                <input type="radio" name="options" id="age"
                       autoComplete="off" onChange={(event)  => this.updateSortBy(event.target.id)} /> Age</label>
            </div>
            <br />

            <div className="mt-3 btn-group btn-group-toggle" data-toggle="buttons">
              <span className="input-group-text">Direction</span>

              <label className="btn btn-secondary active">
                <input type="radio" name="direction" id="asc"
                       autoComplete="off" checked onChange={(event)  => this.updateDirection(event.target.id)}/> Ascending</label>
              <label className="btn btn-secondary">
                <input type="radio" name="direction" id="desc"
                       autoComplete="off" onChange={(event)  => this.updateDirection(event.target.id)} /> Decending</label>
            </div>
            <br />
            <div className={'mt-3'}>
              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">Pagination start</span>
                </div>
                <input type="text" onChange={(event) => this.updateStart(event.target.value)} className="form-control input-sm" aria-label="Enter last name to be filtered by" />
              </div>

              <div className="input-group mb-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">Pagination Limit</span>
                </div>
                <input type="text" onChange={(event) => this.updateLimit(event.target.value)} className="form-control input-sm" aria-label="Enter age to be filtered by" />
              </div>
            </div>

            <div className={'mt-3'}><input className="btn btn-primary" type="button" onClick={() => this.searchFilter()} value="Submit" /></div>

            <Table className="mt-4">
              <thead>
              <tr>
                <th width="10%">Id</th>
                <th width="30%">Name</th>
                <th width="20%">Age</th>
                <th width="20%">Address 1</th>
                <th width="20%">Address 2</th>
              </tr>
              </thead>
              <tbody>
              {userList}
              </tbody>
            </Table>
          </Container>
        </div>
    );
  }
}

export default App;