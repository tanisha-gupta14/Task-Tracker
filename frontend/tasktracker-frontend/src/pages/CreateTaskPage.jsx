import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import API from "../services/api";

function CreateTaskPage() {

  const { id } = useParams();
  const navigate = useNavigate();

  const [form,setForm] = useState({
    title:"",
    description:"",
    dueDate:"",
    priority:"MEDIUM",
    status:"OPEN"
  });

  const handleChange = (e)=>{
    setForm({...form,[e.target.name]:e.target.value});
  }

  const submit = async ()=>{
    await API.post(`/task-lists/${id}/tasks`,form);
    navigate(`/task-list/${id}`);
  }

  return(
    <div className="container">

      <h1 className="page-title">🌸 Create Task</h1>

      <div className="card" style={{maxWidth:"400px"}}>

        <input className="input" name="title" placeholder="Title" onChange={handleChange}/>
        <input className="input" name="description" placeholder="Description" onChange={handleChange}/>

        <input
          className="input"
          type="datetime-local"
          name="dueDate"
          onChange={handleChange}
        />

        <select className="input" name="priority" onChange={handleChange}>
          <option value="HIGH">HIGH</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="LOW">LOW</option>
        </select>

        <select className="input" name="status" onChange={handleChange}>
          <option value="OPEN">OPEN</option>
          <option value="CLOSED">CLOSED</option>
        </select>

        <button className="button" onClick={submit}>
          Create
        </button>

      </div>
    </div>
  );
}

export default CreateTaskPage;