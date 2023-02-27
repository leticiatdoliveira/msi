package org.enib.msi.command;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.enib.msi.impl.MSIModule;
import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.module.IModule;
import org.modelio.api.module.command.DefaultModuleCommandHandler;
import org.modelio.api.module.context.configuration.IModuleUserConfiguration;
import org.modelio.api.module.context.log.ILogService;
import org.modelio.metamodel.uml.statik.Attribute;
import org.modelio.metamodel.uml.statik.Operation;
import org.modelio.vcore.smkernel.mapi.MObject;

/**
 * Implementation of the IModuleContextualCommand interface.
 * <br>The module contextual commands are displayed in the contextual menu and in the specific toolbar of each module property page.
 * <br>The developer may inherit the DefaultModuleContextualCommand class which contains a default standard contextual command implementation.
 *
 */
public class HelloWorld extends DefaultModuleCommandHandler {
    /**
     * Constructor.
     */
    public HelloWorld() {
        super();
    }

    /**
     * @see org.modelio.api.module.commands.DefaultModuleContextualCommand#accept(java.util.List,
     *      org.modelio.api.module.IModule)
     */
    @Override
    public boolean accept(List<MObject> selectedElements, IModule module) {
        // Check that there is only one selected element
        return selectedElements.size() == 1;
    }

    /**
     * @see org.modelio.api.module.commands.DefaultModuleContextualCommand#actionPerformed(java.util.List,
     *      org.modelio.api.module.IModule)
     */
    @Override
    public void actionPerformed(List<MObject> selectedElements, IModule module) {
        ILogService logService = module.getModuleContext().getLogService();
        logService.info("MSI - HelloWorld - actionPerformed(...)");

        IModelingSession session = module.getModuleContext().getModelingSession();
        List<MObject> root = session.getModel().getModelRoots();
        IModuleUserConfiguration configuration = module.getModuleContext().getConfiguration();

        MSIModule msiModule = MSIModule.getInstance();
        
        MObject sel = selectedElements.get(0);
        logService.info(sel.getName() + " / " + sel.getClass().getName());
       
        if(sel.getClass().getName().contains("Package")){

	    	org.modelio.metamodel.uml.statik.Package p = (org.modelio.metamodel.uml.statik.Package)sel;
	    	List<EObject> contents = p.eContents();

	    	for(int i = 0; i < contents.size(); i++) {
	    		EObject elem = contents.get(i);

	    		if (elem.getClass().getName().contains("Class")) {
	    			org.modelio.metamodel.uml.statik.Class c = (org.modelio.metamodel.uml.statik.Class)elem;
	    			logService.info(c.getName()); // print the class name readed

                    // Searching class attributes
	    			List<Attribute> attributesCont = c.getOwnedAttribute();
	    			for(int j = 0; j<attributesCont.size(); j++) {
                        Attribute attr = attributesCont.get(j);

                        if(attr.getClass().getName().contains("Attribute")){
                            org.modelio.metamodel.uml.statik.Attribute a =
                                    (org.modelio.metamodel.uml.statik.Attribute)attr;
                            logService.info(a.getName());
                        }
	    			}
	    			
	    			// Searching class operations
	    			List<Operation> opContents = c.getOwnedOperation();
	    			for(int i1=0; i1<opContents.size(); i1++) {
                        Operation elem_op = opContents.get(i1);

                        if(elem_op.getClass().getName().contains("Operation")){
                            org.modelio.metamodel.uml.statik.Operation o =
                                    (org.modelio.metamodel.uml.statik.Operation)elem_op;
                            logService.info(o.getName());
                        }

                    }

                }
            }
        }
    }
      
}

        
