# Decoupled Asset Relationships in Dataverse
We'd like the ability to loosely relate entities in dataverse with the Dataspace Metadata about assets & publishing.  There are really two primary options.
* Using a simple approach of storing "table name" and "entity id" in the DSPublished table
* Using [Dataverse Connections](https://docs.microsoft.com/en-us/power-apps/developer/data-platform/connection-entities) to establish a loose relationship between any entity in Dataverse and a DSPublish record.

## Decision
Use the simple approach for the MVD since it's easier to implement and has existing UI in the forms to support it.  In the long term, this decision should be revisited since Dataverse connections may offer additional benefits of being easily discoverable from the ribbon button on entities outside of the EDC app could make Dataverse Connections more desirable in the long-term.
