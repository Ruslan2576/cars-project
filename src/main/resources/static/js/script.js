const MAP = new Map([
    ["all", 0],
    ["model", 1],
    ["marka", 2],
    ["color", 3],
    ["speed", 4],
    ["place", 5],
    ["type", 6],
]);

const tbody = document.querySelector('tbody')
let rows = tbody.querySelectorAll('tr')

function sortBy(value) {
    if (!MAP.has(value)) return

    const columnIndex = MAP.get(value)
    rows = [... rows]

    const sortedRows = rows.sort((a, b) => {
        let cellA = a.cells[columnIndex].innerText.trim()
        let cellB = b.cells[columnIndex].innerText.trim()

        if (!isNaN(cellA) && !isNaN(cellB)) {
            return cellA - cellB
        }

        return cellA.localeCompare(cellB)
    })

    sortedRows.forEach(row => tbody.append(row))
}


function search() {
    const input = document.getElementById('globalFilter')
    let value = input.value.toLowerCase()

    let selectedField = document.querySelector('input[name=searchField]:checked').value
    const columnIndex = MAP.get(selectedField)

    for (let row of rows) {
        let cells = row.getElementsByTagName('td')
        if (columnIndex === 0) {
            let find = false
            for (let cell of cells) {
                if (cell.innerText.toLowerCase().startsWith(value)) {
                    find = true
                    break
                }
            }
            row.style.display = find ? '' : 'none'
        } else {
            let cell = row.getElementsByTagName('td')[columnIndex]
            row.style.display = cell.innerText.toLowerCase().startsWith(value) ? '' : 'none'
        }
    }
}