// // delete contact

const baseURL ="http://localhost:8090";


// async function deleteContact(id) {
//   Swal.fire({
//     title: "Do you want to delete the contact?",
//     icon: "warning",
//     showCancelButton: true,
//     confirmButtonText: "Delete",
//   }).then((result) => {
//     /* Read more about isConfirmed, isDenied below */
//     if (result.isConfirmed) {
//       const url = `${baseURL}/user/contacts/delete/` + id;
//       window.location.replace(url);
//     }
//   });
// }


async function deleteContact( id) {

  
    Swal.fire({
      title: "Do you want to delete the contact?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Delete",
      cancelButtonText: "Cancel",
      confirmButtonColor: "#ff0000", // Red color for delete button
      cancelButtonColor: "#6c757d",  // Gray color for cancel button
      customClass: {
        confirmButton: 'custom-confirm-button',
        cancelButton: 'custom-cancel-button'
      }
    }).then((result) => {
      console.log(result)

      console.log("js --->" + id)
      if (result.isConfirmed) {
        const url = `${baseURL}/user/contacts/delete/` + id;
    
        Swal.fire("Deleted!", "", "success");
        window.location.replace(url);
      }
    });
        
  }

// // const baseURL = "http://localhost:8090";

// async function deleteContact(id) {
//     Swal.fire({
//       title: "Do you want to delete the contact?",
//       icon: "warning",
//       showCancelButton: true,
//       confirmButtonText: "Delete",
//       cancelButtonText: "Cancel",
//       confirmButtonColor: "#ff0000",
//       cancelButtonColor: "#6c757d",
//       customClass: {
//         confirmButton: 'custom-confirm-button',
//         cancelButton: 'custom-cancel-button'
//       }
//     }).then(async (result) => {
//       if (result.isConfirmed) {
//         const url = `${baseURL}/user/contacts/delete/` + id;
//         try {
//           const response = await fetch(url, { method: 'DELETE' });
//           if (response.ok) {
//             Swal.fire("Deleted!", "", "success").then(() => {
//               window.location.reload();
//             });
//           } else {
//             Swal.fire("Error!", "Failed to delete the contact.", "error");
//           }
//         } catch (error) {
//           Swal.fire("Error!", "Failed to delete the contact.", "error");
//         }
//       }
//     });
// }


  