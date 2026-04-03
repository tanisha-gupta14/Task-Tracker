import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";

function CreateTaskListPage(){

  const navigate = useNavigate();

  const [form,setForm] = useState({
    title:"",
    description:""
  });

  const handleChange = (e)=>{
    setForm({...form,[e.target.name]:e.target.value});
  }

  const submit = async ()=>{
    if(!form.title) return;

    await API.post("/task-lists",form);

    navigate("/");
  }

  return(
    <div className="container">

      <h1 className="page-title">🌸 Create Task List</h1>

      <div className="card" style={{maxWidth:"400px"}}>

        <input
          className="input"
          name="title"
          placeholder="Title"
          onChange={handleChange}
        />

        <input
          className="input"
          name="description"
          placeholder="Description"
          onChange={handleChange}
        />

        <button className="button" onClick={submit}>
          Create
        </button>

      </div>

    </div>
  );
}

export default CreateTaskListPage;